package com.kerman.core.graphics.k3d.utils

import com.kerman.core.Kerman
import com.kerman.core.assets.AssetManager
import com.kerman.core.graphics.Texture
import com.kerman.core.graphics.k3d.Model
import com.kerman.core.graphics.k3d.model.data.ModelData

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.utils.TextureProvider".
 *
 * Used by [Model] to load textures from [ModelData].
 */
interface TextureProvider {

    fun load(fileName: String): Texture

    class FileTextureProvider @JvmOverloads constructor(
        private val minFilter: Texture.TextureFilter = Texture.TextureFilter.Linear,
        private val magFilter: Texture.TextureFilter = Texture.TextureFilter.Linear,
        private val uWrap: Texture.TextureWrap = Texture.TextureWrap.Repeat,
        private val vWrap: Texture.TextureWrap = Texture.TextureWrap.Repeat,
        private val useMipMaps: Boolean = false
    ) : TextureProvider {

        override fun load(fileName: String): Texture {
            val result = Texture(Kerman.files.internal(fileName), useMipMaps)
            result.setFilter(minFilter, magFilter)
            result.setWrap(uWrap, vWrap)
            return result
        }
    }

    class AssetTextureProvider(val assetManager: AssetManager) : TextureProvider {
        override fun load(fileName: String): Texture = assetManager.get(fileName, Texture::class.java)
    }
}