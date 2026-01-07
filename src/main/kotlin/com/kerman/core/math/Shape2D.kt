package com.kerman.core.math

/**
 * Info : This interface was inspired by "com.badlogic.gdx.math.Shape2D".
 */
interface Shape2D {

    /**
     * Returns whether the given point is contained within the shape.
     */
    fun contains(point: Vector2): Boolean

    /**
     * Returns whether a point with the given coordinates is contained within the shape.
     */
    fun contains(x: Float, y: Float): Boolean
}
