package com.kerman.core.graphics.k3d.environment

import com.kerman.core.graphics.k3d.utils.TextureDescriptor
import com.kerman.core.math.Matrix4

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.environment.ShadowMap".
 */
interface ShadowMap {
    fun getProjViewTrans(): Matrix4
    fun getDepthMap(): TextureDescriptor<*>
}
