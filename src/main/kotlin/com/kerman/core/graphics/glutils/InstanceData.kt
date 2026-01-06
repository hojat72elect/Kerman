package com.kerman.core.graphics.glutils

import com.kerman.core.graphics.VertexAttributes
import com.kerman.core.utils.Disposable
import java.nio.FloatBuffer

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.glutils.InstanceData".
 *
 * An InstanceData instance holds instance data for rendering with OpenGL. It is implemented as either an
 * [InstanceBufferObject] or a [InstanceBufferObjectSubData]. Both require Open GL 3.3+.
 */
interface InstanceData : Disposable {

    /**
     * @return the number of vertices this InstanceData stores
     */
    fun getNumInstances(): Int

    /**
     * @return the number of vertices this InstanceData can store
     */
    fun getNumMaxInstances(): Int

    /**
     * @return the [VertexAttributes] as specified during construction.
     */
    fun getAttributes(): VertexAttributes

    /**
     * Sets the vertices of this InstanceData, discarding the old vertex data. The count must equal the number of floats per
     * vertex times the number of vertices to be copied to this VertexData. The order of the vertex attributes must be the same as
     * specified at construction time via [VertexAttributes].
     * This can be called in between calls to bind and unbind. The vertex data will be updated instantly.
     * @param data   the instance data
     * @param offset the offset to start copying the data from
     * @param count  the number of floats to copy
     */
    fun setInstanceData(data: FloatArray, offset: Int, count: Int)

    /**
     * Update (a portion of) the vertices. Does not resize the backing buffer.
     * @param data         the instance data
     * @param sourceOffset the offset to start copying the data from
     * @param count        the number of floats to copy
     */
    fun updateInstanceData(targetOffset: Int, data: FloatArray, sourceOffset: Int, count: Int)

    /**
     * Sets the vertices of this InstanceData, discarding the old vertex data. The count must equal the number of floats per
     * vertex times the number of vertices to be copied to this InstanceData. The order of the vertex attributes must be the same
     * as specified at construction time via [VertexAttributes].
     * This can be called in between calls to bind and unbind. The vertex data will be updated instantly.
     * @param data  the instance data
     * @param count the number of floats to copy
     */
    fun setInstanceData(data: FloatBuffer, count: Int)

    /**
     * Update (a portion of) the vertices. Does not resize the backing buffer.
     * @param data         the vertex data
     * @param sourceOffset the offset to start copying the data from
     * @param count        the number of floats to copy
     */
    fun updateInstanceData(targetOffset: Int, data: FloatBuffer, sourceOffset: Int, count: Int)

    /**
     * Returns the underlying FloatBuffer and marks it as dirty, causing the buffer contents to be uploaded on the next call to
     * bind. If you need immediate uploading use [.setInstanceData]; Any modifications made to the Buffer
     * *after* the call to bind will not automatically be uploaded.
     * @return the underlying FloatBuffer holding the vertex data.
     * @deprecated use [.getBuffer] instead
     */
    @Deprecated("use getBuffer(boolean) instead")
    fun getBuffer(): FloatBuffer

    /**
     * Returns the underlying FloatBuffer for reading or writing.
     * @param forWriting when true, the underlying buffer will be uploaded on the next call to bind. If you need immediate
     * uploading use [.setInstanceData].
     * @return the underlying FloatBuffer holding the vertex data.
     */
    fun getBuffer(forWriting: Boolean): FloatBuffer

    /**
     * Binds this InstanceData for rendering via glDrawArraysInstanced or glDrawElementsInstanced.
     */
    fun bind(shader: ShaderProgram)

    /**
     * Binds this InstanceData for rendering via glDrawArraysInstanced or glDrawElementsInstanced.
     * @param locations array containing the attribute locations.
     */
    fun bind(shader: ShaderProgram, locations: IntArray?)

    /**
     * Unbinds this InstanceData.
     */
    fun unbind(shader: ShaderProgram)

    /**
     * Unbinds this InstanceData.
     * @param locations array containing the attribute locations.
     */
    fun unbind(shader: ShaderProgram, locations: IntArray?)

    /**
     * Invalidates the InstanceData if applicable. Use this in case of a context loss.
     */
    fun invalidate()

    /**
     * Disposes this InstanceData and all its associated OpenGL resources.
     */
    override fun dispose()
}
