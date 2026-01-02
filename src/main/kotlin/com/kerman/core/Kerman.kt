package com.kerman.core

import com.kerman.core.graphics.GLES20
import com.kerman.core.graphics.GLES30
import com.kerman.core.graphics.GLES31
import com.kerman.core.graphics.GLES32

/**
 * This object was inspired by "com.badlogic.gdx.Gdx".
 *
 * This is the environment object, holding references to the most important resources of our system. This is how we
 * provide static access to all sub systems. Do not use Graphics in a thread that is not the rendering thread.
 */
object Kerman {

    lateinit var app: Application
    lateinit var graphics: Graphics
    lateinit var audio: Audio
    lateinit var input: Input
    lateinit var files: Files
    lateinit var net: Net
    lateinit var gl: GLES20
    lateinit var gl20: GLES20
    lateinit var gl30: GLES30
    lateinit var gl31: GLES31
    lateinit var gl32: GLES32
}