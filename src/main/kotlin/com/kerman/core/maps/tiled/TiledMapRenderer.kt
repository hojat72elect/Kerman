package com.kerman.core.maps.tiled

import com.kerman.core.maps.MapLayer
import com.kerman.core.maps.MapObject
import com.kerman.core.maps.MapRenderer

/**
 * This interface was inspired by "com.badlogic.gdx.maps.tiled.TiledMapRenderer".
 */
interface TiledMapRenderer : MapRenderer {
    fun renderObjects(layer: MapLayer)
    fun renderObject(mapObject: MapObject)
    fun renderTileLayer(layer: TiledMapTileLayer)
    fun renderImageLayer(layer: TiledMapImageLayer)
}
