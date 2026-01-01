package com.kerman.core.utils

/**
 * Info : This interface was inspired by "com.badlogic.gdx.utils.Disposable".
 *
 * Interface for any resource that must be cleaned up when we no longer need it.
 */
interface Disposable {

    /**
     * Release all resources used by this object.
     */
    fun dispose()
}
