package com.kerman.core.assets.loaders;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Cubemap;
import com.kerman.core.graphics.CubemapData;
import com.kerman.core.graphics.Pixmap.Format;
import com.kerman.core.graphics.Texture.TextureFilter;
import com.kerman.core.graphics.Texture.TextureWrap;
import com.kerman.core.graphics.TextureData;
import com.kerman.core.graphics.glutils.KTXTextureData;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.loaders.CubemapLoader".
 * <p>
 * {@link AssetLoader} for {@link Cubemap} instances. The pixel data is loaded asynchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link CubemapParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Cubemap constructors, e.g. filtering and so on.
 */
public class CubemapLoader extends AsynchronousAssetLoader<Cubemap, CubemapLoader.CubemapParameter> {
    CubemapLoaderInfo info = new CubemapLoaderInfo();

    public CubemapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, CubemapParameter parameter) {
        info.filename = fileName;
        if (parameter == null || parameter.cubemapData == null) {
            Format format = null;
            boolean genMipMaps = false;
            info.cubemap = null;

            if (parameter != null) {
                format = parameter.format;
                info.cubemap = parameter.cubemap;
            }

            if (fileName.contains(".ktx") || fileName.contains(".zktx")) {
                info.data = new KTXTextureData(file, genMipMaps);
            }
        } else {
            info.data = parameter.cubemapData;
            info.cubemap = parameter.cubemap;
        }
        if (!info.data.isPrepared()) info.data.prepare();
    }

    @Override
    public Cubemap loadSync(AssetManager manager, String fileName, FileHandle file, CubemapParameter parameter) {
        if (info == null) return null;
        Cubemap cubemap = info.cubemap;
        if (cubemap != null) {
            cubemap.load(info.data);
        } else {
            cubemap = new Cubemap(info.data);
        }
        if (parameter != null) {
            cubemap.setFilter(parameter.minFilter, parameter.magFilter);
            cubemap.setWrap(parameter.wrapU, parameter.wrapV);
        }
        return cubemap;
    }

    @Override
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, CubemapParameter parameter) {
        return null;
    }

    static public class CubemapLoaderInfo {
        String filename;
        CubemapData data;
        Cubemap cubemap;
    }

    static public class CubemapParameter extends AssetLoaderParameters<Cubemap> {
        /**
         * the format of the final Texture. Uses the source images format if null
         **/
        public Format format = null;
        /**
         * The texture to put the {@link TextureData} in, optional.
         **/
        public Cubemap cubemap = null;
        /**
         * CubemapData for textures created on the fly, optional. When set, all format and genMipMaps are ignored
         */
        public CubemapData cubemapData = null;
        public TextureFilter minFilter = TextureFilter.Nearest;
        public TextureFilter magFilter = TextureFilter.Nearest;
        public TextureWrap wrapU = TextureWrap.ClampToEdge;
        public TextureWrap wrapV = TextureWrap.ClampToEdge;
    }
}
