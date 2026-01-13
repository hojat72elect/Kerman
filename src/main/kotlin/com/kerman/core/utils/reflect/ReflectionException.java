package com.kerman.core.utils.reflect;

/**
 * Info : This class was inspired by "com.badlogic.gdx.utils.reflect.ReflectionException".
 * <p>
 * Thrown when an exception occurs during reflection.
 */
public class ReflectionException extends Exception {

    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
