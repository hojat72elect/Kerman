package com.kerman.core

/**
 * This interfaces was inspired by "com.badlogic.gdx.Application".
 *
 * It's going to be the main entry point of your project. It's the equivalent of Android's `Activity`.
 *
 */
interface Application {

    /**
     * The log level of a given application.
     *
     * * [.LOG_NONE] will mute all log output.
     * * [.LOG_ERROR] will only let error messages through.
     * * [.LOG_INFO] will let all non-debug messages through.
     * * [.LOG_DEBUG] will let all messages through.
     */
    val logLevel: LogLevel

    /**
     * @return the [Graphics] instance.
     */
    fun getGraphics(): Graphics

    /**
     * @return the [Audio] instance.
     */
    fun getAudio(): Audio

    /**
     * Logs a message to the console or logcat(depends on the logging system your machine has).
     */
    fun log(tag: String, message: String)

    /**
     * Logs a message to the console or logcat.
     */
    fun log(tag: String, message: String, exception: Throwable)

    /**
     * Logs an error message to the console or logcat.
     */
    fun error(tag: String, message: String)

    /**
     * Logs an error message to the console or logcat.
     */
    fun error(tag: String, message: String, exception: Throwable)

    /**
     * Logs a debug message to the console or logcat.
     */
    fun debug(tag: String, message: String)

    /**
     * Logs a debug message to the console or logcat.
     */
    fun debug(tag: String, message: String, exception: Throwable)

    /**
     * @return the Android API level on Android, the major OS version on iOS (5, 6, 7, ..), or 0 on the desktop.
     */
    fun getVersion(): Int

    /**
     * @return the Java heap memory use in bytes
     */
    fun getJavaHeap(): Long

    /**
     * @return the Native heap memory use in bytes
     */
    fun getNativeHeap(): Long

    /**
     * Posts a [Runnable] on the main loop thread.
     *
     * In a multi-window application, the [Kerman.graphics] might be unpredictable at the
     * time the Runnable is executed. If graphics is needed, it can be copied to a variable to be used in the Runnable.
     * For example:
     * `val graphics = Kerman.graphics`
     *
     * @param runnable the runnable.
     */
    fun postRunnable(runnable: Runnable)

    /**
     * Schedule an exit from the application. On android, this will cause a call to pause() and dispose() some time in the future,
     * it will not immediately finish your application.
     */
    fun exit()
}

enum class LogLevel {
    LOG_NONE, LOG_ERROR, LOG_DEBUG, LOG_INFO
}