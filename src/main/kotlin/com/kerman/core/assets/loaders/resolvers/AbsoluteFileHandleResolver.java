package com.kerman.core.assets.loaders.resolvers;

import com.kerman.core.Kerman;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.files.FileHandle;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver".
 */
public class AbsoluteFileHandleResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return Kerman.files.absolute(fileName);
    }
}
