package com.kerman.core.graphics.glutils;

import com.kerman.core.Kerman;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.GLES30;
import com.kerman.core.graphics.VertexAttribute;
import com.kerman.core.graphics.VertexAttributes;
import com.kerman.core.utils.BufferUtils;
import com.kerman.core.utils.KermanIntArray;
import org.jetbrains.annotations.NotNull;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO".
 * <p>
 * A {@link VertexData} implementation that uses vertex buffer objects and vertex array objects. (This is required for OpenGL 3.0+
 * core profiles. In particular, the default VAO has been deprecated, as has the use of client memory for passing vertex
 * attributes.) Use of VAOs should give a slight performance benefit since you don't have to bind the attributes on every draw
 * anymore.
 * </p>
 *
 * <p>
 * If the OpenGL ES context was lost you can call {@link #invalidate()} to recreate a new OpenGL vertex buffer object.
 * </p>
 *
 * <p>
 * VertexBufferObjectWithVAO objects must be disposed via the {@link #dispose()} method when no longer needed
 * </p>
 * <p>
 * Code adapted from {@link VertexBufferObject}.
 */
public class VertexBufferObjectWithVAO implements VertexData {
    final static IntBuffer tmpHandle = BufferUtils.newIntBuffer(1);

    final VertexAttributes attributes;
    final FloatBuffer buffer;
    final ByteBuffer byteBuffer;
    final boolean ownsBuffer;
    final boolean isStatic;
    final int usage;
    int bufferHandle;
    boolean isDirty = false;
    boolean isBound = false;
    int vaoHandle = -1;
    KermanIntArray cachedLocations = new KermanIntArray();

    /**
     * Constructs a new interleaved VertexBufferObjectWithVAO.
     *
     * @param isStatic    whether the vertex data is static.
     * @param numVertices the maximum number of vertices
     * @param attributes  the {@link com.kerman.core.graphics.VertexAttribute}s.
     */
    public VertexBufferObjectWithVAO(boolean isStatic, int numVertices, VertexAttribute... attributes) {
        this(isStatic, numVertices, new VertexAttributes(attributes));
    }

    /**
     * Constructs a new interleaved VertexBufferObjectWithVAO.
     *
     * @param isStatic    whether the vertex data is static.
     * @param numVertices the maximum number of vertices
     * @param attributes  the {@link VertexAttributes}.
     */
    public VertexBufferObjectWithVAO(boolean isStatic, int numVertices, VertexAttributes attributes) {
        this.isStatic = isStatic;
        this.attributes = attributes;

        byteBuffer = BufferUtils.newUnsafeByteBuffer(this.attributes.vertexSize * numVertices);
        buffer = byteBuffer.asFloatBuffer();
        ownsBuffer = true;
        ((Buffer) buffer).flip();
        ((Buffer) byteBuffer).flip();
        bufferHandle = Kerman.gl20.glGenBuffer();
        usage = isStatic ? GLES20.GL_STATIC_DRAW : GLES20.GL_DYNAMIC_DRAW;
        createVAO();
    }

    public VertexBufferObjectWithVAO(boolean isStatic, ByteBuffer unmanagedBuffer, VertexAttributes attributes) {
        this.isStatic = isStatic;
        this.attributes = attributes;

        byteBuffer = unmanagedBuffer;
        ownsBuffer = false;
        buffer = byteBuffer.asFloatBuffer();
        ((Buffer) buffer).flip();
        ((Buffer) byteBuffer).flip();
        bufferHandle = Kerman.gl20.glGenBuffer();
        usage = isStatic ? GLES20.GL_STATIC_DRAW : GLES20.GL_DYNAMIC_DRAW;
        createVAO();
    }

    @NotNull
    @Override
    public VertexAttributes getAttributes() {
        return attributes;
    }

    @Override
    public int getNumVertices() {
        return buffer.limit() * 4 / attributes.vertexSize;
    }

    @Override
    public int getNumMaxVertices() {
        return byteBuffer.capacity() / attributes.vertexSize;
    }

    /**
     * @deprecated use {@link #getBuffer(boolean)} instead
     */
    @NotNull
    @Override
    @Deprecated
    public FloatBuffer getBuffer() {
        isDirty = true;
        return buffer;
    }

    @NotNull
    @Override
    public FloatBuffer getBuffer(boolean forWriting) {
        isDirty |= forWriting;
        return buffer;
    }

    private void bufferChanged() {
        if (isBound) {
            Kerman.gl20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle);
            Kerman.gl20.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }
    }

    @Override
    public void setVertices(@NotNull float[] vertices, int offset, int count) {
        isDirty = true;
        BufferUtils.copy(vertices, byteBuffer, count, offset);
        ((Buffer) buffer).position(0);
        ((Buffer) buffer).limit(count);
        bufferChanged();
    }

    @Override
    public void updateVertices(int targetOffset, @NotNull float[] vertices, int sourceOffset, int count) {
        isDirty = true;
        final int pos = byteBuffer.position();
        ((Buffer) byteBuffer).position(targetOffset * 4);
        BufferUtils.copy(vertices, sourceOffset, count, byteBuffer);
        ((Buffer) byteBuffer).position(pos);
        ((Buffer) buffer).position(0);
        bufferChanged();
    }

    /**
     * Binds this VertexBufferObject for rendering via glDrawArrays or glDrawElements
     *
     * @param shader the shader
     */
    @Override
    public void bind(@NotNull ShaderProgram shader) {
        bind(shader, null);
    }

    @Override
    public void bind(@NotNull ShaderProgram shader, int[] locations) {
        GLES30 gl = Kerman.gl30;
        gl.glBindVertexArray(vaoHandle);
        bindAttributes(shader, locations);

        // if our data has changed upload it:
        bindData(gl);
        isBound = true;
    }

    private void bindAttributes(ShaderProgram shader, int[] locations) {
        boolean stillValid = this.cachedLocations.size != 0;
        final int numAttributes = attributes.size();

        if (stillValid) {
            if (locations == null) {
                for (int i = 0; stillValid && i < numAttributes; i++) {
                    VertexAttribute attribute = attributes.get(i);
                    int location = shader.getAttributeLocation(attribute.alias);
                    stillValid = location == this.cachedLocations.get(i);
                }
            } else {
                stillValid = locations.length == this.cachedLocations.size;
                for (int i = 0; stillValid && i < numAttributes; i++) {
                    stillValid = locations[i] == this.cachedLocations.get(i);
                }
            }
        }

        if (!stillValid) {
            Kerman.gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle);
            unbindAttributes(shader);
            this.cachedLocations.clear();

            for (int i = 0; i < numAttributes; i++) {
                VertexAttribute attribute = attributes.get(i);
                if (locations == null) {
                    this.cachedLocations.add(shader.getAttributeLocation(attribute.alias));
                } else {
                    this.cachedLocations.add(locations[i]);
                }

                int location = this.cachedLocations.get(i);
                if (location < 0) {
                    continue;
                }

                shader.enableVertexAttribute(location);
                shader.setVertexAttribute(location, attribute.numComponents, attribute.type, attribute.normalized,
                        attributes.vertexSize, attribute.offset);
            }
        }
    }

    private void unbindAttributes(ShaderProgram shaderProgram) {
        if (cachedLocations.size == 0) {
            return;
        }
        int numAttributes = attributes.size();
        for (int i = 0; i < numAttributes; i++) {
            int location = cachedLocations.get(i);
            if (location < 0) {
                continue;
            }
            shaderProgram.disableVertexAttribute(location);
        }
    }

    private void bindData(GLES20 gl) {
        if (isDirty) {
            gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle);
            ((Buffer) byteBuffer).limit(buffer.limit() * 4);
            gl.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }
    }

    /**
     * Unbinds this VertexBufferObject.
     *
     * @param shader the shader
     */
    @Override
    public void unbind(@NotNull final ShaderProgram shader) {
        unbind(shader, null);
    }

    @Override
    public void unbind(@NotNull final ShaderProgram shader, final int[] locations) {
        GLES30 gl = Kerman.gl30;
        gl.glBindVertexArray(0);
        isBound = false;
    }

    /**
     * Invalidates the VertexBufferObject so a new OpenGL buffer handle is created. Use this in case of a context loss.
     */
    @Override
    public void invalidate() {
        bufferHandle = Kerman.gl30.glGenBuffer();
        createVAO();
        isDirty = true;
    }

    /**
     * Disposes of all resources this VertexBufferObject uses.
     */
    @Override
    public void dispose() {
        GLES30 gl = Kerman.gl30;

        gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        gl.glDeleteBuffer(bufferHandle);
        bufferHandle = 0;
        if (ownsBuffer) {
            BufferUtils.disposeUnsafeByteBuffer(byteBuffer);
        }
        deleteVAO();
    }

    private void createVAO() {
        ((Buffer) tmpHandle).clear();
        Kerman.gl30.glGenVertexArrays(1, tmpHandle);
        vaoHandle = tmpHandle.get();
    }

    private void deleteVAO() {
        if (vaoHandle != -1) {
            ((Buffer) tmpHandle).clear();
            tmpHandle.put(vaoHandle);
            ((Buffer) tmpHandle).flip();
            Kerman.gl30.glDeleteVertexArrays(1, tmpHandle);
            vaoHandle = -1;
        }
    }
}
