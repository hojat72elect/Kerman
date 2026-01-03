package com.kerman.core

/**
 * This interface was inspired by "com.badlogic.gdx.LifecycleListener".
 *
 * A LifecycleListener can be added to an [Application] via [Application.addLifecycleListener]. It
 * will receive notification of pause, resume and dispose events. This is mainly meant to be used by extensions that need to
 * manage resources based on the life-cycle. Normal, application level development should rely on the [ApplicationListener]
 * interface.
 *
 * The functions will be invoked on the rendering thread. These functions will be executed before the [ApplicationListener]
 * functions are executed.
 */
interface LifecycleListener {
    /**
     * Called when the [Application] is about to pause
     */
    fun pause()

    /**
     * Called when the [Application] is about to be resumed
     */
    fun resume()

    /**
     * Called when the [Application] is about to be disposed
     */
    fun dispose()
}