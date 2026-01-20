package com.kerman.examples.sokoban

import com.kerman.examples.sokoban.screens.MainMenuScreen
import com.kerman.examples.sokoban.screens.Screens
import com.kerman.core.Game
import com.kerman.core.graphics.k2d.SpriteBatch
import com.kerman.core.scenes.scene2d.Stage
import com.kerman.core.utils.I18NBundle
import com.kerman.core.utils.viewport.StretchViewport

class SokobanGame : Game() {

    var stage: Stage? = null
    var batch: SpriteBatch? = null
    var languages: I18NBundle? = null

    override fun create() {
        stage = Stage(StretchViewport(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT))
        batch = SpriteBatch()

        Assets.load()
        Settings.load()

        setScreen(MainMenuScreen(this))
    }
}