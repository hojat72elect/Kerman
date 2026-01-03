package com.kerman.core.assets.loaders;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.files.FileHandle;
import com.kerman.core.utils.KermanArray;

/**
 * Abstract base class for asset loaders.
 *
 * @param <T> the class of the "asset" that the loader supports.
 * @param <P> the class of the "loading parameters" the loader supports.
 *  */
public abstract class AssetLoader<T, P extends AssetLoaderParameters<T>> {
    /**
     * {@link FileHandleResolver} used to map from plain asset names to {@link FileHandle} instances
     **/
    private final FileHandleResolver resolver;

    /**
     * Constructor, sets the {@link FileHandleResolver} to use to resolve the file associated with the asset name.
     */
    public AssetLoader(FileHandleResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * @param fileName file name to resolve
     * @return handle to the file, as resolved by the {@link FileHandleResolver} set on the loader
     */
    public FileHandle resolve(String fileName) {
        return resolver.resolve(fileName);
    }

    /**
     * Returns the assets this asset requires to be loaded first. This method may be called on a thread other than the GL thread.
     *
     * @param fileName  name of the asset to load
     * @param file      the resolved file to load
     * @param parameter parameters for loading the asset
     * @return other assets that the asset depends on and need to be loaded first or null if there are no dependencies.
     */
    public abstract KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, P parameter);
}
