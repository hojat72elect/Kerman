package com.kerman.core.utils;

/**
 * Info : This class was inspired by "com.badlogic.gdx.utils.Collections".
 */
public class Collections {

    /**
     * When true, {@link Iterable#iterator()} for {KermanArray}, {ObjectMap}, and other collections will allocate a new
     * iterator for each invocation. When false, the iterator is reused and nested use will throw an exception. Default is
     * false.
     */
    public static boolean allocateIterators;
}
