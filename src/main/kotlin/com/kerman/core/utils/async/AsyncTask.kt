package com.kerman.core.utils.async

/**
 * Info : This interface was inspired by "com.badlogic.gdx.utils.async.AsyncTask".
 *
 * Task to be submitted to an [AsyncExecutor], returning a result of type T.
 */
interface AsyncTask<T> {
    @Throws(Exception::class)
    fun call(): T
}
