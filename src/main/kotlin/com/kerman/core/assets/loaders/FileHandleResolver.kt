package com.kerman.core.assets.loaders

import com.kerman.core.files.FileHandle
import com.kerman.core.assets.AssetManager

/**
 * This interface was inspired by "com.badlogic.gdx.assets.loaders.FileHandleResolver".
 *
 * Interface for classes which can map a file name to a [FileHandle]. Used to allow the [AssetManager] to load
 * resources from anywhere or implement caching strategies.
 */
interface FileHandleResolver {
    fun resolve(fileName: String): FileHandle
}
