package com.kerman.core.utils

import kotlin.math.max

/**
 * This abstract class was inspired by "com.badlogic.gdx.utils.Pool".
 *
 * A pool of objects that can be reused to avoid allocation.
 * @see PoolManager
 * @param initialCapacity The initial size of the array supporting the pool. No objects are created/pre-allocated. Use [.fill] after instantiation if needed.
 * @param max The maximum number of free objects that can be stored in this pool.
 */
abstract class Pool<T> @JvmOverloads constructor(initialCapacity: Int = 16, @JvmField val max: Int = Int.MAX_VALUE) {

    private val freeObjects = KermanArray<T>(false, initialCapacity)

    /**
     * The highest number of free objects. Can be reset at any time.
     */
    var peak = 0

    protected abstract fun newObject(): T

    /**
     * Returns an object from this pool. The object may be new (from [.newObject]) or reused (previously
     * [freed][.free]).
     */
    open fun obtain(): T = if (freeObjects.size == 0) newObject() else freeObjects.pop()

    /**
     * Puts the specified object in the pool, making it eligible to be returned by [.obtain]. If the pool already contains
     * [.max] free objects, the specified object is [discarded][.discard], it is not reset and not added to the
     * pool.
     *
     * The pool does not check if an object is already freed, so the same object must not be freed multiple times.
     */
    open fun free(pooledObject: T) {
        if (freeObjects.size < max) {
            freeObjects.add(pooledObject)
            peak = max(peak, freeObjects.size)
            reset(pooledObject)
        } else discard(pooledObject)
    }

    /**
     * Adds the specified number of new free objects to the pool. Usually called early on as a pre-allocation mechanism but can be
     * used at any time.
     *
     * @param size the number of objects to be added
     */
    fun fill(size: Int) {
        for (i in 0..<size)
            if (freeObjects.size < max)
                freeObjects.add(newObject())
        peak = max(peak, freeObjects.size)
    }

    /**
     * Called when an object is freed to clear the state of the object for possible later reuse. The default implementation calls
     * [Poolable.reset] if the object is [Poolable].
     */
    protected open fun reset(pooledObject: T) {
        if (pooledObject is Poolable) pooledObject.reset()
    }

    /**
     * Called when an object is discarded. This is the case when an object is freed, but the maximum capacity of the pool is
     * reached, and when the pool is [cleared][.clear]
     */
    protected open fun discard(pooledObject: T) {
        reset(pooledObject)
    }

    /**
     * Puts the specified objects in the pool. Null objects within the array are silently ignored.
     * The pool does not check if an object is already freed, so the same object must not be freed multiple times.
     * @see .free
     */
    open fun freeAll(pooledObjects: Array<T>) {
        val freeObjects = this.freeObjects
        val max = this.max
        var i = 0
        val n = pooledObjects.size
        while (i < n) {
            val pooledObject = pooledObjects.get(i)
            if (pooledObject == null) {
                i++
                continue
            }
            if (freeObjects.size < max) {
                freeObjects.add(pooledObject)
                reset(pooledObject)
            } else {
                discard(pooledObject)
            }
            i++
        }
        peak = max(peak, freeObjects.size)
    }

    /**
     * Removes and discards all free objects from this pool.
     */
    open fun clear() {
        val freeObjects = this.freeObjects
        var i = 0
        val n = freeObjects.size
        while (i < n) {
            discard(freeObjects.get(i))
            i++
        }
        freeObjects.clear()
    }

    /**
     * The number of objects available to be obtained.
     */
    fun getFree() = freeObjects.size

    /**
     * Objects implementing this interface will have [.reset] called when passed to [Pool.free].
     */
    interface Poolable {
        /**
         * Resets the object for reuse. Object references should be nulled and fields may be set to default values.
         */
        fun reset()
    }
}
