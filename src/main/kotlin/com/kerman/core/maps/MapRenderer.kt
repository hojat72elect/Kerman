package com.kerman.core.maps

import com.kerman.core.graphics.OrthographicCamera
import com.kerman.core.math.Matrix4

/**
 * Info : This interface was inspired by "com.badlogic.gdx.maps.MapRenderer".
 *
 * This interface describes a common way of rendering [KermanMap] objects.
 */
interface MapRenderer {

    /**
     * Sets the projection matrix and viewbounds from the given camera. If the camera changes, you have to call this method again.
     * The viewbounds are taken from the camera's position and viewport size as well as the scale. This method will only work if
     * the camera's direction vector is (0,0,-1) and its up vector is (0, 1, 0), which are the defaults.
     * @param camera the [OrthographicCamera]
     */
    fun setView(camera: OrthographicCamera)

    /**
     * Sets the projection matrix for rendering, as well as the bounds of the map which should be rendered. Make sure that the
     * frustum spanned by the projection matrix coincides with the viewbounds.
     */
    fun setView(projectionMatrix: Matrix4, viewboundsX: Float, viewboundsY: Float, viewboundsWidth: Float, viewboundsHeight: Float)

    /**
     * Renders all the layers of a map.
     */
    fun render()

    /**
     * Renders the given layers of a map.
     * @param layers the layers to render.
     */
    fun render(layers: IntArray)
}
