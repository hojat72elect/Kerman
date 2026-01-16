package com.kerman.core.assets.loaders.resolvers;

import com.kerman.core.Kerman;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.files.FileHandle;
import org.jetbrains.annotations.NotNull;

/**
 * Info : This class was inspired by "com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver".
 */
public class ExternalFileHandleResolver implements FileHandleResolver {
    @Override
    @NotNull
    public FileHandle resolve(@NotNull String fileName) {
        return Kerman.files.external(fileName);
    }
}
