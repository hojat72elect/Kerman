package com.kerman.core.graphics.glutils

import com.kerman.core.graphics.Color
import com.kerman.core.math.Matrix4

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer".
 */
interface ImmediateModeRenderer {
    fun begin(projModelView: Matrix4?, primitiveType: Int)

    fun flush()

    fun color(color: Color?)

    fun color(r: Float, g: Float, b: Float, a: Float)

    fun color(colorBits: Float)

    fun texCoord(u: Float, v: Float)

    fun normal(x: Float, y: Float, z: Float)

    fun vertex(x: Float, y: Float, z: Float)

    fun end()

    fun getNumVertices(): Int

    fun getMaxVertices(): Int

    fun dispose()
}
