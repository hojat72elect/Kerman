package com.kerman.examples.sokoban.game

import com.kerman.examples.sokoban.Assets
import com.kerman.examples.sokoban.screens.Screens
import com.kerman.core.graphics.OrthographicCamera
import com.kerman.core.maps.tiled.TiledMapTileLayer
import com.kerman.core.maps.tiled.renderers.OrthogonalTiledMapRenderer

class BoardRenderer {

    private val camera = OrthographicCamera(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT)
    private val renderer = OrthogonalTiledMapRenderer(Assets.map, GameBoard.Companion.UNIT_SCALE)
    private val tileLayer = renderer.map.layers.get("StaticMap") as TiledMapTileLayer?

    init {
        camera.position.set(Screens.SCREEN_WIDTH / 2F, Screens.SCREEN_HEIGHT / 2F, 0F)

    }

    fun render() {
        camera.update()
        renderer.setView(camera)
        renderer.batch.begin()
        renderer.renderTileLayer(tileLayer!!)
        renderer.batch.end()
    }
}