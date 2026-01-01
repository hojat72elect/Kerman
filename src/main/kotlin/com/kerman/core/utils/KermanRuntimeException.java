package com.kerman.core.utils;

/**
 * This class was inspired by "com.badlogic.gdx.utils.GdxRuntimeException".
 * <p>
 * Typed runtime exception used throughout Kerman game engine.
 */
public class KermanRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6735854402467673117L;

    public KermanRuntimeException(String message) {
        super(message);
    }

    public KermanRuntimeException(Throwable t) {
        super(t);
    }

    public KermanRuntimeException(String message, Throwable t) {
        super(message, t);
    }
}
