package com.kerman.core

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
}