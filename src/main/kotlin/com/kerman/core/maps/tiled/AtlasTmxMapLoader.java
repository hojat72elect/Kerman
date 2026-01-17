package com.kerman.core.maps.tiled;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.assets.loaders.TextureLoader;
import com.kerman.core.assets.loaders.resolvers.InternalFileHandleResolver;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Texture;
import com.kerman.core.graphics.k2d.TextureAtlas;
import com.kerman.core.graphics.k2d.TextureAtlas.AtlasRegion;
import com.kerman.core.graphics.k2d.TextureRegion;
import com.kerman.core.maps.ImageResolver;
import com.kerman.core.maps.MapProperties;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.KermanRuntimeException;
import com.kerman.core.utils.XmlReader.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader".
 * <p>
 * A TiledMap Loader which loads tiles from a TextureAtlas instead of separate images.
 * <p>
 * It requires a map-level property called 'atlas' with its value being the relative path to the TextureAtlas. The atlas must have
 * in it indexed regions named after the tilesets used in the map. The indexes shall be local to the tileset (not the global id).
 * Strip whitespace and rotation should not be used when creating the atlas.
 */
public class AtlasTmxMapLoader extends BaseTmxMapLoader<BaseTiledMapLoader.Parameters> {

    protected KermanArray<Texture> trackedTextures = new KermanArray<>();
    protected AtlasResolver atlasResolver;

    public AtlasTmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public AtlasTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Parse incoming region name to check for 'atlas_imagelayer' within the String These are regions representing Image Layers
     * that have been packed into the atlas ImageLayer Image names include the relative assets path, so it must be stripped.
     *
     * @param name Name to check
     * @return The name of the region to pass into an atlas
     */
    static String parseRegionName(String name) {
        if (name.contains("atlas_imagelayer")) {
            // Find the last '/' in the path
            int lastSlash = name.lastIndexOf('/');
            // If we found a slash, return everything after it which should be our region name
            // If no slashes found return entire string
            return (lastSlash >= 0) ? name.substring(lastSlash + 1) : name;
        } else {
            return name;
        }
    }

    public TiledMap load(String fileName) {
        return load(fileName, new Parameters());
    }

    public TiledMap load(String fileName, Parameters parameter) {
        FileHandle tmxFile = resolve(fileName);

        this.root = xml.parse(tmxFile);

        final FileHandle atlasFileHandle = getAtlasFileHandle(tmxFile);
        TextureAtlas atlas = new TextureAtlas(atlasFileHandle);
        this.atlasResolver = new AtlasResolver.DirectAtlasResolver(atlas);

        TiledMap map = loadTiledMap(tmxFile, parameter, atlasResolver);
        map.setOwnedResources(new KermanArray<>(new TextureAtlas[]{atlas}));
        setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        return map;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
        FileHandle atlasHandle = getAtlasFileHandle(tmxFile);
        this.atlasResolver = new AtlasResolver.AssetManagerAtlasResolver(manager, atlasHandle.path());

        this.map = loadTiledMap(tmxFile, parameter, atlasResolver);
    }

    @Override
    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        if (parameter != null) {
            setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        }

        return map;
    }

    @Override
    protected KermanArray<AssetDescriptor> getDependencyAssetDescriptors(FileHandle tmxFile,
                                                                   TextureLoader.TextureParameter textureParameter) {
        KermanArray<AssetDescriptor> descriptors = new KermanArray<>();

        // Atlas dependencies
        final FileHandle atlasFileHandle = getAtlasFileHandle(tmxFile);
        if (atlasFileHandle != null) {
            descriptors.add(new AssetDescriptor(atlasFileHandle, TextureAtlas.class));
        }

        return descriptors;
    }

    @Override
    protected void addStaticTiles(FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, Element element,
                                  KermanArray<Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin,
                                  String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {

        TextureAtlas atlas = atlasResolver.getAtlas();
        String regionsName = name;

        for (Texture texture : atlas.getTextures()) {
            trackedTextures.add(texture);
        }

        MapProperties props = tileSet.getProperties();
        props.put("imagesource", imageSource);
        props.put("imagewidth", imageWidth);
        props.put("imageheight", imageHeight);
        props.put("tilewidth", tilewidth);
        props.put("tileheight", tileheight);
        props.put("margin", margin);
        props.put("spacing", spacing);

        if (imageSource != null && !imageSource.isEmpty()) {
            int lastgid = firstgid + ((imageWidth / tilewidth) * (imageHeight / tileheight)) - 1;
            for (AtlasRegion region : atlas.findRegions(regionsName)) {
                // Handle unused tileIds
                if (region != null) {
                    int tileId = firstgid + region.index;
                    if (tileId >= firstgid && tileId <= lastgid) {
                        addStaticTiledMapTile(tileSet, region, tileId, offsetX, offsetY);
                    }
                }
            }
        }

        // Add tiles with individual image sources
        for (Element tileElement : tileElements) {
            int tileId = firstgid + tileElement.getIntAttribute("id", 0);
            TiledMapTile tile = tileSet.getTile(tileId);
            if (tile == null) {
                Element imageElement = tileElement.getChildByName("image");
                if (imageElement != null) {
                    String regionName = imageElement.getAttribute("source");
                    regionName = regionName.substring(0, regionName.lastIndexOf('.'));
                    AtlasRegion region = atlas.findRegion(regionName);
                    if (region == null) throw new KermanRuntimeException("Tileset atlasRegion not found: " + regionName);
                    addStaticTiledMapTile(tileSet, region, tileId, offsetX, offsetY);
                }
            }
        }
    }

    protected FileHandle getAtlasFileHandle(FileHandle tmxFile) {
        Element properties = root.getChildByName("properties");

        String atlasFilePath = null;
        if (properties != null) {
            for (Element property : properties.getChildrenByName("property")) {
                String name = property.getAttribute("name");
                if (name.startsWith("atlas")) {
                    atlasFilePath = property.getAttribute("value");
                    break;
                }
            }
        }
        if (atlasFilePath == null) {
            throw new KermanRuntimeException("The map is missing the 'atlas' property");
        } else {
            final FileHandle fileHandle = getRelativeFileHandle(tmxFile, atlasFilePath);
            if (!fileHandle.exists()) {
                throw new KermanRuntimeException("The 'atlas' file could not be found: '" + atlasFilePath + "'");
            }
            return fileHandle;
        }
    }

    protected void setTextureFilters(Texture.TextureFilter min, Texture.TextureFilter mag) {
        for (Texture texture : trackedTextures) {
            texture.setFilter(min, mag);
        }
        trackedTextures.clear();
    }

    protected interface AtlasResolver extends ImageResolver {

        TextureAtlas getAtlas();

        class DirectAtlasResolver implements AtlasResolver {
            private final TextureAtlas atlas;

            public DirectAtlasResolver(TextureAtlas atlas) {
                this.atlas = atlas;
            }

            @Override
            public TextureAtlas getAtlas() {
                return atlas;
            }

            @Override
            public TextureRegion getImage(@NotNull String name) {
                // check for imagelayer and strip if needed
                String regionName = parseRegionName(name);
                return atlas.findRegion(regionName);
            }
        }

        class AssetManagerAtlasResolver implements AtlasResolver {
            private final AssetManager assetManager;
            private final String atlasName;

            public AssetManagerAtlasResolver(AssetManager assetManager, String atlasName) {
                this.assetManager = assetManager;
                this.atlasName = atlasName;
            }

            @Override
            public TextureAtlas getAtlas() {
                return assetManager.get(atlasName, TextureAtlas.class);
            }

            @Override
            public TextureRegion getImage(@NotNull String name) {
                // check for imagelayer and strip if needed
                String regionName = parseRegionName(name);
                return getAtlas().findRegion(regionName);
            }
        }
    }
}
