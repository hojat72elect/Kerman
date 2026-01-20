package com.kerman.examples.sokoban.scene2d

import com.kerman.examples.sokoban.Assets
import com.kerman.examples.sokoban.screens.Screens
import com.kerman.core.graphics.Color
import com.kerman.core.scenes.scene2d.Group
import com.kerman.core.scenes.scene2d.InputEvent
import com.kerman.core.scenes.scene2d.Stage
import com.kerman.core.scenes.scene2d.actions.Actions
import com.kerman.core.scenes.scene2d.ui.Button
import com.kerman.core.scenes.scene2d.ui.Image
import com.kerman.core.scenes.scene2d.ui.Label
import com.kerman.core.scenes.scene2d.ui.Label.LabelStyle
import com.kerman.core.scenes.scene2d.ui.Table
import com.kerman.core.scenes.scene2d.utils.ClickListener
import com.kerman.core.scenes.scene2d.utils.TextureRegionDrawable

open class WindowGroup(protected var screen: Screens, width: Float, height: Float, positionY: Float) : Group() {
    private val dim: Image

    var isShown: Boolean = false
        private set

    init {
        setSize(width, height)
        y = positionY

        dim = Image(Assets.blackPixelDrawable)
        dim.setSize(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT)

        setBackGround(Assets.windowBackground)
    }

    protected fun setCloseButton() {
        val buttonClose = Button(Assets.buttonCloseDrawable, Assets.buttonClosePressedDrawable)
        buttonClose.setSize(60f, 60f)
        buttonClose.setPosition(290f, 250f)
        buttonClose.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                hide()
            }
        })
        addActor(buttonClose)
    }

    protected fun setTitle(text: String?, fontScale: Float) {
        val tableTitle = Table()

        tableTitle.setSize(180f, 50f)
        tableTitle.setPosition(width / 2f - tableTitle.width / 2f, height - tableTitle.height)

        val labelTitle = Label(text, LabelStyle(Assets.font, Color.WHITE))
        labelTitle.setFontScale(fontScale)
        tableTitle.add(labelTitle)
        addActor(tableTitle)
    }

    private fun setBackGround(imageBackground: TextureRegionDrawable?) {
        val image = Image(imageBackground)
        image.setSize(width, height)
        addActor(image)
    }

    fun show(stage: Stage) {
        setOrigin(width / 2f, height / 2f)
        x = Screens.SCREEN_WIDTH / 2f - width / 2f

        setScale(.35f)
        addAction(Actions.scaleTo(1f, 1f, ANIMATION_DURATION))

        dim.color.a = 0f
        dim.addAction(Actions.alpha(.7f, ANIMATION_DURATION))

        isShown = true
        stage.addActor(dim)
        stage.addActor(this)
    }

    fun hide() {
        isShown = false

        addAction(
            Actions
                .sequence(Actions.scaleTo(.35f, .35f, ANIMATION_DURATION), Actions.removeActor(dim), Actions.removeActor())
        )
        dim.addAction(Actions.alpha(0f, ANIMATION_DURATION))
    }

    companion object {
        const val ANIMATION_DURATION: Float = .3f
    }
}
