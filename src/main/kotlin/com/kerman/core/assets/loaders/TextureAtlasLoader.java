package com.kerman.core.assets.loaders;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.assets.loaders.TextureLoader.TextureParameter;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Texture;
import com.kerman.core.graphics.k2d.TextureAtlas;
import com.kerman.core.graphics.k2d.TextureAtlas.TextureAtlasData;
import com.kerman.core.graphics.k2d.TextureAtlas.TextureAtlasData.Page;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by the "com.badlogic.gdx.assets.loaders.TextureAtlasLoader".
 * <p>
 * {@link AssetLoader} to load {@link TextureAtlas} instances. Passing a {@link TextureAtlasParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows to specify whether the atlas regions should be flipped
 * on the y-axis or not.
 */
public class TextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {
    TextureAtlasData data;

    public TextureAtlasLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public TextureAtlas load(AssetManager assetManager, String fileName, FileHandle file, TextureAtlasParameter parameter) {
        for (Page page : data.getPages()) {
            Texture texture = assetManager.get(page.textureFile.path().replaceAll("\\\\", "/"), Texture.class);
            page.texture = texture;
        }

        TextureAtlas atlas = new TextureAtlas(data);
        data = null;
        return atlas;
    }

    @Override
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle atlasFile, TextureAtlasParameter parameter) {
        FileHandle imgDir = atlasFile.parent();

        if (parameter != null)
            data = new TextureAtlasData(atlasFile, imgDir, parameter.flip);
        else {
            data = new TextureAtlasData(atlasFile, imgDir, false);
        }

        KermanArray<AssetDescriptor> dependencies = new KermanArray();
        for (Page page : data.getPages()) {
            TextureParameter params = new TextureParameter();
            params.format = page.format;
            params.genMipMaps = page.useMipMaps;
            params.minFilter = page.minFilter;
            params.magFilter = page.magFilter;
            dependencies.add(new AssetDescriptor(page.textureFile, Texture.class, params));
        }
        return dependencies;
    }

    static public class TextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {
        /**
         * whether to flip the texture atlas vertically
         **/
        public boolean flip = false;

        public TextureAtlasParameter() {
        }

        public TextureAtlasParameter(boolean flip) {
            this.flip = flip;
        }
    }
}
