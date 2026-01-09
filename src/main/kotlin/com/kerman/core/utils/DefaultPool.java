package com.kerman.core.utils;

/**
 * Info : This class was inspired by "com.badlogic.gdx.utils.DefaultPool".
 * <p>
 * Default Pool implementation that creates new instances of a type based on a supplier.
 */
public class DefaultPool<T> extends Pool<T> {

    private final PoolSupplier<T> poolTypeSupplier;

    public DefaultPool(PoolSupplier<T> supplier) {
        this(supplier, 16, Integer.MAX_VALUE);
    }

    public DefaultPool(PoolSupplier<T> supplier, int initialCapacity) {
        this(supplier, initialCapacity, Integer.MAX_VALUE);
    }

    public DefaultPool(PoolSupplier<T> supplier, int initialCapacity, int max) {
        super(initialCapacity, max);
        this.poolTypeSupplier = supplier;
    }

    @Override
    protected T newObject() {
        return poolTypeSupplier.get();
    }

    /**
     * An interface that is used to create objects for the Pool. Even tho not annotated with "FunctionalInterface", it can act as
     * one. <br>
     * You can use a constructor reference or lambda as a PoolSupplier, such as with {@code MyClass::new} or
     * {@code () -> new MyClass()}.
     */
    public interface PoolSupplier<T> {
        T get();
    }
}
