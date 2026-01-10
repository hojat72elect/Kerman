package com.kerman.core.scenes.scene2d.utils

import com.kerman.core.math.Rectangle

/**
 * Info : This interface was inspired by "com.badlogic.gdx.scenes.scene2d.utils.Cullable".
 *
 * Allows a parent to set the area that is visible on a child actor to allow the child to cull when drawing itself. This must
 * only be used for actors that are not rotated or scaled.
 */
interface Cullable {

    /**
     * @param cullingArea The culling area in the child actor's coordinates.
     */
    fun setCullingArea(cullingArea: Rectangle?)
}
