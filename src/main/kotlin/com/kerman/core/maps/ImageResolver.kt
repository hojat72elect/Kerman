package com.kerman.core.maps

import com.kerman.core.assets.AssetManager
import com.kerman.core.graphics.Texture
import com.kerman.core.graphics.k2d.TextureAtlas
import com.kerman.core.graphics.k2d.TextureRegion
import com.kerman.core.utils.ObjectMap

/**
 * Info : This interface was inspired by "com.badlogic.gdx.maps.ImageResolver".
 *
 * Resolves an image by a string, wrapper around a Map or [AssetManager] to load maps either directly or via [AssetManager].
 */
interface ImageResolver {

    /**
     * @return the [Texture] for the given image name or null.
     */
    fun getImage(name: String): TextureRegion?

    class DirectImageResolver(private val images: ObjectMap<String, Texture>) : ImageResolver {
        override fun getImage(name: String) = TextureRegion(images.get(name))
    }

    class AssetManagerImageResolver(private val assetManager: AssetManager) : ImageResolver {
        override fun getImage(name: String) = TextureRegion(assetManager.get(name, Texture::class.java))
    }

    class TextureAtlasImageResolver(private val atlas: TextureAtlas) : ImageResolver {
        override fun getImage(name: String): TextureRegion = atlas.findRegion(name)!!
    }
}
