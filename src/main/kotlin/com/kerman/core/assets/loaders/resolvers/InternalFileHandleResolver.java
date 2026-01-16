package com.kerman.core.assets.loaders.resolvers;

import com.kerman.core.Kerman;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.files.FileHandle;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver".
 */
public class InternalFileHandleResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return Kerman.files.internal(fileName);
    }
}
