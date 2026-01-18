package com.kerman.core.graphics.k3d.utils

import com.kerman.core.graphics.GLTexture

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.utils.TextureBinder".
 *
 * Responsible for binding textures, may implement a strategy to avoid binding a texture unnecessarily. A TextureBinder may
 * decide to which texture unit it binds a texture.
 */
interface TextureBinder {

    /**
     * Prepares the binder for operation, must be matched with a call to [end].
     */
    fun begin()

    /**
     * Disables all used texture units and unbinds textures. Resets the counts.
     */
    fun end()

    /**
     * Binds the texture to an available unit and applies the filters in the descriptor.
     *
     * @param textureDescriptor the [TextureDescriptor]
     * @return the unit the texture was bound to.
     */
    fun bind(textureDescriptor: TextureDescriptor<*>): Int

    /**
     * Binds the texture to an available unit.
     *
     * @param texture the [GLTexture]
     * @return the unit the texture was bound to
     */
    fun bind(texture: GLTexture): Int

    /**
     * @return the number of binds actually executed since the last call to [resetCounts].
     */
    fun getBindCount(): Int

    /**
     * @return the number of binds that could be avoided by reuse.
     */
    fun getReuseCount(): Int

    /**
     * Resets the bind/reuse counts
     */
    fun resetCounts()

}
