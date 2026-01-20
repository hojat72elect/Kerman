package com.kerman.examples.sokoban.scene2d

import com.kerman.examples.sokoban.Assets
import com.kerman.core.graphics.Color
import com.kerman.core.scenes.scene2d.ui.Label
import com.kerman.core.scenes.scene2d.ui.Label.LabelStyle
import com.kerman.core.scenes.scene2d.ui.Table
import com.kerman.core.scenes.scene2d.utils.TextureRegionDrawable

class CounterTable(backgroundDrawable: TextureRegionDrawable, x: Float, y: Float) : Table() {


    private val displayLabel = Label("", LabelStyle(Assets.fontRed, Color.WHITE))

    init {
        this.setBounds(x, y, WIDTH, HEIGHT)
        background = backgroundDrawable

        displayLabel.setFontScale(.8f)
        add(displayLabel)

        center()
        padLeft(25f)
        padBottom(5f)
    }

    fun updateDisplayedNumber(newNumber: Int) {
        displayLabel.setText(newNumber)
    }

    companion object {
        private const val WIDTH = 125F
        private const val HEIGHT = 42F
    }
}