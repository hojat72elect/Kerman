package com.kerman.core.scenes.scene2d.utils

import com.kerman.core.graphics.k2d.Batch

/**
 * Info : This interface was inspired by "com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable".
 *
 * A drawable that supports scale and rotation.
 */
interface TransformDrawable : Drawable {
    fun draw(batch: Batch, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, scaleX: Float, scaleY: Float, rotation: Float)
}
