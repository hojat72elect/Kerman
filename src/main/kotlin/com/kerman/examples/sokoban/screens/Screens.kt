package com.kerman.examples.sokoban.screens

import com.kerman.examples.sokoban.Assets
import com.kerman.examples.sokoban.Assets.loadTiledMap
import com.kerman.examples.sokoban.Settings.save
import com.kerman.examples.sokoban.SokobanGame
import com.kerman.examples.sokoban.game.GameScreen
import com.kerman.core.Kerman
import com.kerman.core.InputAdapter
import com.kerman.core.InputMultiplexer
import com.kerman.core.Screen
import com.kerman.core.graphics.GLES20
import com.kerman.core.graphics.OrthographicCamera
import com.kerman.core.input.GestureDetector
import com.kerman.core.input.GestureDetector.GestureListener
import com.kerman.core.math.Vector2
import com.kerman.core.scenes.scene2d.Actor
import com.kerman.core.scenes.scene2d.InputEvent
import com.kerman.core.scenes.scene2d.InputListener
import com.kerman.core.scenes.scene2d.actions.Actions
import com.kerman.core.scenes.scene2d.ui.Image
import kotlin.math.abs

abstract class Screens(val game: SokobanGame) : InputAdapter(), Screen, GestureListener {


    private var camera = OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT)
    var batch = game.batch
    var stage = game.stage

    override fun render(delta: Float) {
        update(delta)

        Kerman.gl.glClearColor(0f, 0f, 0f, 1f)
        Kerman.gl.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        camera.update()
        batch!!.setProjectionMatrix(camera.combined)
        draw(delta)

        stage!!.act(delta)
        stage!!.draw()
    }

    private var blackFadeOut: Image? = null

    init {
        stage?.clear()
        camera.position[SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f] = 0f
        val gestureDetector = GestureDetector(20f, .5f, 2f, .15f, this)
        Kerman.input.setInputProcessor(InputMultiplexer(this, gestureDetector, stage))
    }

    fun changeScreenWithFadeOut(newScreen: Class<*>, level: Int, game: SokobanGame) {
        blackFadeOut = Image(Assets.blackPixelDrawable)
        blackFadeOut!!.setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
        blackFadeOut!!.color.a = 0f
        blackFadeOut!!.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.run {
            if (newScreen == GameScreen::class.java) {
                loadTiledMap(level)
                game.screen = GameScreen(game, level)
            } else if (newScreen == MainMenuScreen::class.java) game.screen = MainMenuScreen(game)
        }))
        stage!!.addActor(blackFadeOut)
    }

    fun changeScreenWithFadeOut(newScreen: Class<*>, game: SokobanGame) {
        changeScreenWithFadeOut(newScreen, -1, game)
    }

    fun addPressEffect(actor: Actor) {
        actor.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                actor.setPosition(actor.x, actor.y - 5)
                event.stop()
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                actor.setPosition(actor.x, actor.y + 5)
            }
        })
    }

    abstract fun draw(delta: Float)

    abstract fun update(delta: Float)

    override fun resize(width: Int, height: Int) {
        stage!!.viewport.update(width, height, true)
    }

    override fun show() {
    }

    override fun hide() {
        save()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
        stage!!.dispose()
        batch!!.dispose()
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (abs(velocityX.toDouble()) > abs(velocityY.toDouble())) {
            if (velocityX > 0) {
                right()
            } else {
                left()
            }
        } else {
            if (velocityY > 0) {
                down()
            } else {
                up()
            }
        }
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return false
    }

    override fun pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean {
        return false
    }

    open fun up() {
        Kerman.app.log("UP", "")
    }

    open fun down() {
        Kerman.app.log("DOWN", "")
    }

    open fun left() {
        Kerman.app.log("LEFT", "")
    }

    open fun right() {
        Kerman.app.log("RIGHT", "")
    }

    override fun pinchStop() {
    }

    companion object {
        const val SCREEN_WIDTH = 800F
        const val SCREEN_HEIGHT = 480F
    }
}