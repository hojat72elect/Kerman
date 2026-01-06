package com.kerman.core.graphics.glutils;

import com.kerman.core.Kerman;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.VertexAttribute;
import com.kerman.core.graphics.VertexAttributes;
import com.kerman.core.utils.BufferUtils;
import com.kerman.core.utils.KermanRuntimeException;

import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.glutils.InstanceBufferObjectSubData".
 * <p>
 * Modification of the {@link VertexBufferObjectSubData} class. Sets the glVertexAttribDivisor for every {@link VertexAttribute}
 * automatically.
 */
public class InstanceBufferObjectSubData implements InstanceData {

    final VertexAttributes attributes;
    final FloatBuffer buffer;
    final ByteBuffer byteBuffer;
    final boolean isDirect;
    final boolean isStatic;
    final int usage;
    int bufferHandle;
    boolean isDirty = false;
    boolean isBound = false;

    /**
     * Constructs a new interleaved InstanceBufferObject.
     *
     * @param isStatic           whether the vertex data is static.
     * @param numInstances       the maximum number of vertices
     * @param instanceAttributes the {@link VertexAttributes}.
     */
    public InstanceBufferObjectSubData(boolean isStatic, int numInstances, VertexAttribute... instanceAttributes) {
        this(isStatic, numInstances, new VertexAttributes(instanceAttributes));
    }

    /**
     * Constructs a new interleaved InstanceBufferObject.
     *
     * @param isStatic           whether the vertex data is static.
     * @param numInstances       the maximum number of vertices
     * @param instanceAttributes the {@link VertexAttribute}s.
     */
    public InstanceBufferObjectSubData(boolean isStatic, int numInstances, VertexAttributes instanceAttributes) {
        this.isStatic = isStatic;
        this.attributes = instanceAttributes;
        byteBuffer = BufferUtils.newByteBuffer(this.attributes.vertexSize * numInstances);
        isDirect = true;

        usage = isStatic ? GLES20.GL_STATIC_DRAW : GLES20.GL_DYNAMIC_DRAW;
        buffer = byteBuffer.asFloatBuffer();
        bufferHandle = createBufferObject();
        ((Buffer) buffer).flip();
        ((Buffer) byteBuffer).flip();
    }

    private int createBufferObject() {
        int result = Kerman.gl20.glGenBuffer();
        Kerman.gl20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, result);
        Kerman.gl20.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.capacity(), null, usage);
        Kerman.gl20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return result;
    }

    @NotNull
    @Override
    public VertexAttributes getAttributes() {
        return attributes;
    }

    /**
     * Effectively returns {#getNumInstances()}.
     *
     * @return number of instances in this buffer
     */
    @Override
    public int getNumInstances() {
        return buffer.limit() * 4 / attributes.vertexSize;
    }

    /**
     * Effectively returns {#getNumMaxInstances()}.
     *
     * @return maximum number of instances in this buffer
     */
    @Override
    public int getNumMaxInstances() {
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
            Kerman.gl20.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), null, usage);
            Kerman.gl20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, byteBuffer.limit(), byteBuffer);
            isDirty = false;
        }
    }

    @Override
    public void setInstanceData(@NotNull float[] data, int offset, int count) {
        isDirty = true;
        if (isDirect) {
            BufferUtils.copy(data, byteBuffer, count, offset);
            ((Buffer) buffer).position(0);
            ((Buffer) buffer).limit(count);
        } else {
            ((Buffer) buffer).clear();
            buffer.put(data, offset, count);
            ((Buffer) buffer).flip();
            ((Buffer) byteBuffer).position(0);
            ((Buffer) byteBuffer).limit(buffer.limit() << 2);
        }

        bufferChanged();
    }

    @Override
    public void setInstanceData(@NotNull FloatBuffer data, int count) {
        isDirty = true;
        if (isDirect) {
            BufferUtils.copy(data, byteBuffer, count);
            ((Buffer) buffer).position(0);
            ((Buffer) buffer).limit(count);
        } else {
            ((Buffer) buffer).clear();
            buffer.put(data);
            ((Buffer) buffer).flip();
            ((Buffer) byteBuffer).position(0);
            ((Buffer) byteBuffer).limit(buffer.limit() << 2);
        }

        bufferChanged();
    }

    @Override
    public void updateInstanceData(int targetOffset, @NotNull float[] data, int sourceOffset, int count) {
        isDirty = true;
        if (isDirect) {
            final int pos = byteBuffer.position();
            ((Buffer) byteBuffer).position(targetOffset * 4);
            BufferUtils.copy(data, sourceOffset, count, byteBuffer);
            ((Buffer) byteBuffer).position(pos);
        } else
            throw new KermanRuntimeException("Buffer must be allocated direct."); // Should never happen

        bufferChanged();
    }

    @Override
    public void updateInstanceData(int targetOffset, @NotNull FloatBuffer data, int sourceOffset, int count) {
        isDirty = true;
        if (isDirect) {
            final int pos = byteBuffer.position();
            ((Buffer) byteBuffer).position(targetOffset * 4);
            ((Buffer) data).position(sourceOffset * 4);
            BufferUtils.copy(data, byteBuffer, count);
            ((Buffer) byteBuffer).position(pos);
        } else
            throw new KermanRuntimeException("Buffer must be allocated direct."); // Should never happen

        bufferChanged();
    }

    /**
     * Binds this InstanceBufferObject for rendering via glDrawArraysInstanced or glDrawElementsInstanced
     *
     * @param shader the shader
     */
    @Override
    public void bind(@NotNull final ShaderProgram shader) {
        bind(shader, null);
    }

    @Override
    public void bind(@NotNull final ShaderProgram shader, final int[] locations) {
        final GLES20 gl = Kerman.gl20;

        gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle);
        if (isDirty) {
            ((Buffer) byteBuffer).limit(buffer.limit() * 4);
            gl.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }

        final int numAttributes = attributes.size();
        if (locations == null) {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = shader.getAttributeLocation(attribute.alias);
                if (location < 0) continue;
                int unitOffset = +attribute.unit;
                shader.enableVertexAttribute(location + unitOffset);

                shader.setVertexAttribute(location + unitOffset, attribute.numComponents, attribute.type, attribute.normalized,
                        attributes.vertexSize, attribute.offset);
                Kerman.gl30.glVertexAttribDivisor(location + unitOffset, 1);
            }
        } else {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = locations[i];
                if (location < 0) continue;
                int unitOffset = +attribute.unit;
                shader.enableVertexAttribute(location + unitOffset);

                shader.setVertexAttribute(location + unitOffset, attribute.numComponents, attribute.type, attribute.normalized,
                        attributes.vertexSize, attribute.offset);
                Kerman.gl30.glVertexAttribDivisor(location + unitOffset, 1);
            }
        }
        isBound = true;
    }

    /**
     * Unbinds this InstanceBufferObject.
     *
     * @param shader the shader
     */
    @Override
    public void unbind(@NotNull final ShaderProgram shader) {
        unbind(shader, null);
    }

    @Override
    public void unbind(@NotNull final ShaderProgram shader, final int[] locations) {
        final GLES20 gl = Kerman.gl20;
        final int numAttributes = attributes.size();
        if (locations == null) {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = shader.getAttributeLocation(attribute.alias);
                if (location < 0) continue;
                int unitOffset = +attribute.unit;
                shader.disableVertexAttribute(location + unitOffset);
            }
        } else {
            for (int i = 0; i < numAttributes; i++) {
                final VertexAttribute attribute = attributes.get(i);
                final int location = locations[i];
                if (location < 0) continue;
                int unitOffset = +attribute.unit;
                shader.enableVertexAttribute(location + unitOffset);
            }
        }
        gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        isBound = false;
    }

    /**
     * Invalidates the InstanceBufferObject so a new OpenGL buffer handle is created. Use this in case of a context loss.
     */
    public void invalidate() {
        bufferHandle = createBufferObject();
        isDirty = true;
    }

    /**
     * Disposes of all resources this InstanceBufferObject uses.
     */
    @Override
    public void dispose() {
        GLES20 gl = Kerman.gl20;
        gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        gl.glDeleteBuffer(bufferHandle);
        bufferHandle = 0;
    }

    /**
     * Returns the InstanceBufferObject handle
     *
     * @return the InstanceBufferObject handle
     */
    public int getBufferHandle() {
        return bufferHandle;
    }
}
