package com.kerman.core.maps.tiled

import com.kerman.core.graphics.k2d.TextureRegion
import com.kerman.core.maps.MapObjects
import com.kerman.core.maps.MapProperties

/**
 * Info : This interface was inspired by "com.badlogic.gdx.maps.tiled.TiledMapTile".
 *
 *  Generalizes the concept of tile in a TiledMap.
 */
interface TiledMapTile {

    fun getId(): Int

    fun setId(id: Int)

    /**
     * @return the [BlendMode] to use for rendering the tile.
     */
    fun getBlendMode(): BlendMode

    /**
     * Sets the [BlendMode] to use for rendering the tile.
     * @param blendMode the blend mode to use for rendering the tile.
     */
    fun setBlendMode(blendMode: BlendMode)

    /**
     * @return texture region used to render the tile
     */
    fun getTextureRegion(): TextureRegion

    /**
     * Sets the texture region used to render the tile.
     */
    fun setTextureRegion(textureRegion: TextureRegion)

    /**
     * @return the amount to offset the x position when rendering the tile.
     */
    fun getOffsetX(): Float

    /**
     * Set the amount to offset the x position when rendering the tile.
     */
    fun setOffsetX(offsetX: Float)

    /**
     * @return the amount to offset the y position when rendering the tile.
     */
    fun getOffsetY(): Float

    /**
     * Set the amount to offset the y position when rendering the tile.
     */
    fun setOffsetY(offsetY: Float)

    /**
     * @return tile's properties set. It might be null (in case no properties are set).
     */
    fun getProperties(): MapProperties?

    /**
     * @return collection of objects contained in the tile
     */
    fun getObjects(): MapObjects

    enum class BlendMode {
        NONE, ALPHA
    }
}
