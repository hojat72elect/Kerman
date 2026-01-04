package com.kerman.core.assets.loaders;

import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.files.FileHandle;

/**
 * Info : This abstract class was inspired by "com.badlogic.gdx.assets.loaders.SynchronousAssetLoader".
 */
public abstract class SynchronousAssetLoader<T, P extends AssetLoaderParameters<T>> extends AssetLoader<T, P> {
    public SynchronousAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public abstract T load(AssetManager assetManager, String fileName, FileHandle file, P parameter);
}
