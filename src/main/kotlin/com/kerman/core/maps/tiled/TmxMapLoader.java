package com.kerman.core.maps.tiled;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.assets.loaders.TextureLoader;
import com.kerman.core.assets.loaders.resolvers.InternalFileHandleResolver;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Texture;
import com.kerman.core.graphics.k2d.TextureRegion;
import com.kerman.core.maps.ImageResolver;
import com.kerman.core.maps.ImageResolver.AssetManagerImageResolver;
import com.kerman.core.maps.ImageResolver.DirectImageResolver;
import com.kerman.core.maps.MapProperties;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.ObjectMap;
import com.kerman.core.utils.XmlReader.Element;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.TmxMapLoader".
 * <p>
 * synchronous loader for TMX maps created with the Tiled tool
 */
public class TmxMapLoader extends BaseTmxMapLoader<BaseTiledMapLoader.Parameters> {

    public TmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    /**
     * Creates loader
     */
    public TmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Loads the {@link TiledMap} from the given file. The file is resolved via the {@link FileHandleResolver} set in the
     * constructor of this class. By default it will resolve to an internal file. The map will be loaded for a y-up coordinate
     * system.
     *
     * @param fileName the filename
     * @return the TiledMap
     */
    public TiledMap load(String fileName) {
        return load(fileName, new Parameters());
    }

    /**
     * Loads the {@link TiledMap} from the given file. The file is resolved via the {@link FileHandleResolver} set in the
     * constructor of this class. By default it will resolve to an internal file.
     *
     * @param fileName  the filename
     * @param parameter specifies whether to use y-up, generate mip maps etc.
     * @return the TiledMap
     */
    public TiledMap load(String fileName, Parameters parameter) {
        FileHandle tmxFile = resolve(fileName);

        this.root = xml.parse(tmxFile);

        ObjectMap<String, Texture> textures = new ObjectMap<String, Texture>();

        final KermanArray<FileHandle> textureFiles = getDependencyFileHandles(tmxFile);
        for (FileHandle textureFile : textureFiles) {
            Texture texture = new Texture(textureFile, parameter.generateMipMaps);
            texture.setFilter(parameter.textureMinFilter, parameter.textureMagFilter);
            textures.put(textureFile.path(), texture);
        }

        TiledMap map = loadTiledMap(tmxFile, parameter, new DirectImageResolver(textures));
        map.setOwnedResources(textures.values().toArray());
        return map;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
        this.map = loadTiledMap(tmxFile, parameter, new AssetManagerImageResolver(manager));
    }

    @Override
    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return map;
    }

    @Override
    protected KermanArray<AssetDescriptor> getDependencyAssetDescriptors(FileHandle tmxFile,
                                                                   TextureLoader.TextureParameter textureParameter) {
        KermanArray<AssetDescriptor> descriptors = new KermanArray<AssetDescriptor>();

        final KermanArray<FileHandle> fileHandles = getDependencyFileHandles(tmxFile);
        for (FileHandle handle : fileHandles) {
            descriptors.add(new AssetDescriptor(handle, Texture.class, textureParameter));
        }

        return descriptors;
    }

    protected KermanArray<FileHandle> getDependencyFileHandles(FileHandle tmxFile) {
        KermanArray<FileHandle> fileHandles = new KermanArray<FileHandle>();

        // TileSet descriptors
        for (Element tileset : root.getChildrenByNameRecursively("tileset")) {
            getTileSetDependencyFileHandle(fileHandles, tmxFile, tileset);
        }

        // ImageLayer descriptors
        for (Element imageLayer : root.getChildrenByNameRecursively("imagelayer")) {
            Element image = imageLayer.getChildByName("image");
            String source = image.getAttribute("source", null);

            if (source != null) {
                FileHandle handle = getRelativeFileHandle(tmxFile, source);
                fileHandles.add(handle);
            }
        }

        return fileHandles;
    }

    protected KermanArray<FileHandle> getTileSetDependencyFileHandle(FileHandle tmxFile, Element tileset) {
        KermanArray<FileHandle> fileHandles = new KermanArray<FileHandle>();
        return getTileSetDependencyFileHandle(fileHandles, tmxFile, tileset);
    }

    protected KermanArray<FileHandle> getTileSetDependencyFileHandle(KermanArray<FileHandle> fileHandles, FileHandle tmxFile,
                                                               Element tileset) {
        FileHandle tsxFile;
        String source = tileset.getAttribute("source", null);
        if (source != null) {
            tsxFile = getRelativeFileHandle(tmxFile, source);
            tileset = xml.parse(tsxFile);
        } else {
            tsxFile = tmxFile;
        }
        Element imageElement = tileset.getChildByName("image");
        if (imageElement != null) {
            String imageSource = imageElement.getAttribute("source");
            FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
            fileHandles.add(image);
        } else {
            for (Element tile : tileset.getChildrenByName("tile")) {
                String imageSource = tile.getChildByName("image").getAttribute("source");
                FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
                fileHandles.add(image);
            }
        }
        return fileHandles;
    }

    @Override
    protected void addStaticTiles(FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, Element element,
                                  KermanArray<Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin,
                                  String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {

        MapProperties props = tileSet.getProperties();
        if (image != null) {
            // One image for the whole tileSet
            TextureRegion texture = imageResolver.getImage(image.path());

            props.put("imagesource", imageSource);
            props.put("imagewidth", imageWidth);
            props.put("imageheight", imageHeight);
            props.put("tilewidth", tilewidth);
            props.put("tileheight", tileheight);
            props.put("margin", margin);
            props.put("spacing", spacing);

            int stopWidth = texture.getRegionWidth() - tilewidth;
            int stopHeight = texture.getRegionHeight() - tileheight;

            int id = firstgid;

            for (int y = margin; y <= stopHeight; y += tileheight + spacing) {
                for (int x = margin; x <= stopWidth; x += tilewidth + spacing) {
                    TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
                    int tileId = id++;
                    addStaticTiledMapTile(tileSet, tileRegion, tileId, offsetX, offsetY);
                }
            }
        } else {
            // Every tile has its own image source
            for (Element tileElement : tileElements) {
                Element imageElement = tileElement.getChildByName("image");
                if (imageElement != null) {
                    imageSource = imageElement.getAttribute("source");

                    if (source != null) {
                        image = getRelativeFileHandle(getRelativeFileHandle(tmxFile, source), imageSource);
                    } else {
                        image = getRelativeFileHandle(tmxFile, imageSource);
                    }
                }
                TextureRegion texture = imageResolver.getImage(image.path());
                int tileId = firstgid + tileElement.getIntAttribute("id");
                addStaticTiledMapTile(tileSet, texture, tileId, offsetX, offsetY);
            }
        }
    }
}
