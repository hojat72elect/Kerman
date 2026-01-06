package com.kerman.core.graphics.glutils

import com.kerman.core.utils.Disposable
import java.nio.ShortBuffer

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.glutils.IndexData".
 * An [IndexData] instance holds index data. Can be either a plain short buffer or an OpenGL buffer object.
 */
interface IndexData : Disposable {

    /**
     * @return the number of indices currently stored in this buffer
     */
    fun getNumIndices(): Int

    /**
     * @return the maximum number of indices this IndexBufferObject can store.
     */
    fun getNumMaxIndices(): Int

    /**
     * Sets the indices of this IndexBufferObject, discarding the old indices. The count must equal the number of indices to be
     * copied to this IndexBufferObject.
     * This can be called in between calls to [.bind] and [.unbind]. The index data will be updated instantly.
     * @param indices the index data
     * @param offset  the offset to start copying the data from
     * @param count   the number of shorts to copy
     */
    fun setIndices(indices: ShortArray, offset: Int, count: Int)

    /**
     * Copies the specified indices to the indices of this IndexBufferObject, discarding the old indices. Copying start at the
     * current [ShortBuffer.position] of the specified buffer and copied the [ShortBuffer.remaining] amount of
     * indices. This can be called in between calls to [.bind] and [.unbind]. The index data will be updated
     * instantly.
     * @param indices the index data to copy
     */
    fun setIndices(indices: ShortBuffer)

    /**
     * Update (a portion of) the indices.
     * @param targetOffset offset in indices buffer
     * @param indices the index data
     * @param offset the offset to start copying the data from
     * @param count the number of shorts to copy
     */
    fun updateIndices(targetOffset: Int, indices: ShortArray, offset: Int, count: Int)

    @Deprecated("use {@link #getBuffer(boolean)} instead")
    fun getBuffer(): ShortBuffer

    /**
     * Returns the underlying ShortBuffer for reading or writing.
     * @param forWriting when true, the underlying buffer will be uploaded on the next call to [.bind]. If you need
     * immediate uploading use [.setIndices].
     * @return the underlying short buffer.
     */
    fun getBuffer(forWriting: Boolean): ShortBuffer

    /**
     * Binds this IndexBufferObject for rendering with glDrawElements.
     */
    fun bind()

    /**
     * Unbinds this IndexBufferObject.
     */
    fun unbind()

    /**
     * Invalidates the IndexBufferObject so a new OpenGL buffer handle is created. Use this in case of a context loss.
     */
    fun invalidate()

    /**
     * Disposes this IndexData and all its associated OpenGL resources.
     */
    override fun dispose()
}
