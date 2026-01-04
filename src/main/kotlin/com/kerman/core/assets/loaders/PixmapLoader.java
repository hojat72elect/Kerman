package com.kerman.core.assets.loaders;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Pixmap;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.loaders.PixmapLoader".
 * <p>
 * {@link AssetLoader} for {@link Pixmap} instances. The Pixmap is loaded asynchronously.
 */
public class PixmapLoader extends AsynchronousAssetLoader<Pixmap, PixmapLoader.PixmapParameter> {
    Pixmap pixmap;

    public PixmapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, PixmapParameter parameter) {
        pixmap = null;
        pixmap = new Pixmap(file);
    }

    @Override
    public Pixmap loadSync(AssetManager manager, String fileName, FileHandle file, PixmapParameter parameter) {
        Pixmap pixmap = this.pixmap;
        this.pixmap = null;
        return pixmap;
    }

    @Override
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, PixmapParameter parameter) {
        return null;
    }

    static public class PixmapParameter extends AssetLoaderParameters<Pixmap> {
    }
}
