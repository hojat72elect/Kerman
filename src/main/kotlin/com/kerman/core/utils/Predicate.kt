package com.kerman.core.utils

/**
 * Info : this interface was inspired by "com.badlogic.gdx.utils.Predicate".
 *
 * Interface used to select items within an iterator against a predicate.
 */
interface Predicate<T> {

    /**
     * @return true if the item matches the criteria and should be included in the iterator's items
     */
    fun evaluate(arg0: T): Boolean

    class PredicateIterator<T>(iterator: Iterator<T>, predicate: Predicate<T>) : Iterator<T> {
        var iterator: Iterator<T> = iterator
            private set
        var predicate: Predicate<T> = predicate
            private set
        var end = false
            private set
        var peeked = false
            private set
        var next: T? = null
            private set

        constructor(iterable: Iterable<T>, predicate: Predicate<T>) : this(iterable.iterator(), predicate)

        fun set(iterable: Iterable<T>, predicate: Predicate<T>) {
            set(iterable.iterator(), predicate)
        }

        fun set(iterator: Iterator<T>, predicate: Predicate<T>) {
            this.iterator = iterator
            this.predicate = predicate
            end = false
            peeked = false
            next = null
        }

        override fun hasNext(): Boolean {
            if (end) return false
            if (next != null) return true
            peeked = true
            while (iterator.hasNext()) {
                val n = iterator.next()
                if (predicate.evaluate(n)) {
                    next = n
                    return true
                }
            }
            end = true
            return false
        }

        override fun next(): T {
            if (next == null && !hasNext()) throw Error()
            val result = next!!
            next = null
            peeked = false
            return result
        }

        fun remove() {
            if (peeked) throw KermanRuntimeException("Cannot remove between a call to hasNext() and next().")
            (iterator as MutableIterator<T>).remove()
        }
    }

    class PredicateIterable<T>(
        iterable: Iterable<T>,
        predicate: Predicate<T>
    ) : Iterable<T> {
        var iterable: Iterable<T> = iterable
            private set
        var predicate: Predicate<T> = predicate
            private set
        var iterator: PredicateIterator<T>? = null
            private set

        init {
            set(iterable, predicate)
        }

        fun set(iterable: Iterable<T>, predicate: Predicate<T>) {
            this.iterable = iterable
            this.predicate = predicate
        }

        /**
         * Returns an iterator. Remove is supported.
         *
         * If [Collections.allocateIterators] is false, the same iterator instance is returned each time this method is
         * called. Use the [Predicate.PredicateIterator] constructor for nested or multithreaded iteration.
         */
        override fun iterator(): Iterator<T> {
            if (Collections.allocateIterators) return PredicateIterator(iterable.iterator(), predicate)
            if (iterator == null) {
                iterator = PredicateIterator(iterable.iterator(), predicate)
            } else {
                iterator!!.set(iterable.iterator(), predicate)
            }
            return iterator!!
        }
    }
}
