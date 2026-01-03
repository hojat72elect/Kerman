package com.kerman.core.assets;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.AssetLoaderParameters".
 */
public class AssetLoaderParameters<T> {

    public LoadedCallback loadedCallback;

    /**
     * Callback interface that will be invoked when the {@link AssetManager} loads an asset.
     */
    public interface LoadedCallback {
        void finishedLoading(AssetManager assetManager, String fileName, Class type);
    }
}
