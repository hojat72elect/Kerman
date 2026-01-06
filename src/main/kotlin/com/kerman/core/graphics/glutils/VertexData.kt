package com.kerman.core.graphics.glutils

import com.kerman.core.graphics.VertexAttributes
import com.kerman.core.utils.Disposable
import java.nio.FloatBuffer

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.glutils.VertexData".
 *
 * A [VertexData] instance holds vertices for rendering with OpenGL. It is implemented as either a [VertexArray] or a
 * [VertexBufferObject]. Only the later supports OpenGL ES 2.0.
 */
interface VertexData : Disposable {

    /**
     * @return the number of vertices this VertexData stores
     */
    fun getNumVertices(): Int

    /**
     * @return the number of vertices this VertedData can store
     */
    fun getNumMaxVertices(): Int

    /**
     * @return the [VertexAttributes] as specified during construction.
     */
    fun getAttributes(): VertexAttributes

    /**
     * Sets the vertices of this VertexData, discarding the old vertex data. The count must equal the number of floats per vertex
     * times the number of vertices to be copied to this VertexData. The order of the vertex attributes must be the same as
     * specified at construction time via [VertexAttributes].
     * This can be called in between calls to bind and unbind. The vertex data will be updated instantly.
     * @param vertices the vertex data
     * @param offset   the offset to start copying the data from
     * @param count    the number of floats to copy
     */
    fun setVertices(vertices: FloatArray, offset: Int, count: Int)

    /**
     * Update (a portion of) the vertices. Does not resize the backing buffer.
     * @param vertices     the vertex data
     * @param sourceOffset the offset to start copying the data from
     * @param count        the number of floats to copy
     */
    fun updateVertices(targetOffset: Int, vertices: FloatArray, sourceOffset: Int, count: Int)

    /**
     * Returns the underlying FloatBuffer and marks it as dirty, causing the buffer contents to be uploaded on the next call to
     * bind. If you need immediate uploading use [.setVertices]; Any modifications made to the Buffer
     * *after* the call to bind will not automatically be uploaded.
     * @return the underlying FloatBuffer holding the vertex data.
     * @deprecated use [.getBuffer] instead.
     */
    @Deprecated("use {@link #getBuffer(boolean)} instead.")
    fun getBuffer(): FloatBuffer

    /**
     * Returns the underlying FloatBuffer for reading or writing.
     * @param forWriting when true, the underlying buffer will be uploaded on the next call to bind. If you need immediate
     * uploading use [.setVertices].
     * @return the underlying FloatBuffer holding the vertex data.
     */
    fun getBuffer(forWriting: Boolean): FloatBuffer

    /**
     * Binds this VertexData for rendering via glDrawArrays or glDrawElements.
     */
    fun bind(shader: ShaderProgram)

    /**
     * Binds this VertexData for rendering via glDrawArrays or glDrawElements.
     * @param locations array containing the attribute locations.
     */
    fun bind(shader: ShaderProgram, locations: IntArray?)

    /**
     * Unbinds this VertexData.
     */
    fun unbind(shader: ShaderProgram)

    /**
     * Unbinds this VertexData.
     * @param locations array containing the attribute locations.
     */
    fun unbind(shader: ShaderProgram, locations: IntArray?)

    /**
     * Invalidates the VertexData if applicable. Use this in case of a context loss.
     */
    fun invalidate()

    /**
     * Disposes this VertexData and all its associated OpenGL resources.
     */
    override fun dispose()
}
