package com.kerman.core.utils;

/**
 * Typed runtime exception used throughout libGDX
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
