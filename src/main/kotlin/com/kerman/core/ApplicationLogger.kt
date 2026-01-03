package com.kerman.core

/**
 * Info : This interface was inspired by "com.badlogic.gdx.ApplicationLogger".
 *
 * The ApplicationLogger provides an interface for a Kerman Application to log any messages and exceptions. The default implementation
 * is provided for each backend, custom implementations can be provided and set by using [Application.setApplicationLogger].
 */
interface ApplicationLogger {

    /**
     * Logs a message with a tag
     */
    fun log(tag: String, message: String)

    /**
     * Logs a message and exception with a tag
     */
    fun log(tag: String, message: String, exception: Throwable)

    /**
     * Logs an error message with a tag
     */
    fun error(tag: String, message: String)

    /**
     * Logs an error message and exception with a tag
     */
    fun error(tag: String, message: String, exception: Throwable)

    /**
     * Logs a debug message with a tag
     */
    fun debug(tag: String, message: String)

    /**
     * Logs a debug message and exception with a tag
     */
    fun debug(tag: String, message: String, exception: Throwable)
}
