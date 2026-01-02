package com.kerman.core.utils

/**
 * Info : This interface was inspired by "com.badlogic.gdx.utils.ArraySupplier".
 *
 * An interface that is used to create arrays. Even though not annotated with "FunctionalInterface", it can act as one.
 * You can use a constructor reference or lambda as an [ArraySupplier], such as with `MyClass[]::new` or
 * `(size) -> new MyClass[size]`.
 */
fun interface ArraySupplier<T> {

    fun get(size: Int): T

    companion object {
        /**
         * A default array supplier that creates an Object[].
         */
        @JvmField
        val ANY: ArraySupplier<*> = ArraySupplier { size -> arrayOfNulls<Any>(size) }

        /**
         * Returns a default array supplier that creates an Object[].
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> objects(): ArraySupplier<Array<T>> {
            return ANY as ArraySupplier<Array<T>>
        }
    }
}
