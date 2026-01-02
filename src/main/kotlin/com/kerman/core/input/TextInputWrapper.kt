package com.kerman.core.input

/**
 * This interface was inspired by "com.badlogic.gdx.input.TextInputWrapper".
 */
interface TextInputWrapper {

    fun getText(): String

    fun setText(text: String)

    fun getSelectionStart(): Int

    fun getSelectionEnd(): Int

    fun setPosition(position: Int)

    fun shouldClose(): Boolean
}
