package com.kerman.core.graphics.k3d.utils

import com.kerman.core.graphics.Camera
import com.kerman.core.graphics.k3d.Renderable
import com.kerman.core.utils.KermanArray

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.utils.RenderableSorter".
 *
 * Responsible for sorting [Renderable] lists by whatever criteria (material, distance to camera, etc.)
 */
interface RenderableSorter {

    /**
     * Sorts the array of [Renderable] instances based on some criteria, e.g. material, distance to camera etc.
     * @param renderables the array of renderables to be sorted.
     */
    fun sort(camera: Camera, renderables: KermanArray<Renderable>)
}
