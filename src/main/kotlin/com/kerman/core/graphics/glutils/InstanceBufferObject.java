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
 * Info : This class was inspired by "com.badlogic.gdx.graphics.glutils.InstanceBufferObject".
 * <p>
 * This is a modification of the {@link VertexBufferObject} class. Sets the glVertexAttribDivisor for every {@link VertexAttribute}
 * automatically.
 */
public class InstanceBufferObject implements InstanceData {

    boolean isDirty = false;
    boolean isBound = false;
    private VertexAttributes attributes;
    private FloatBuffer buffer;
    private ByteBuffer byteBuffer;
    private boolean ownsBuffer;
    private int bufferHandle;
    private int usage;

    public InstanceBufferObject(boolean isStatic, int numVertices, VertexAttribute... attributes) {
        this(isStatic, numVertices, new VertexAttributes(attributes));
    }

    public InstanceBufferObject(boolean isStatic, int numVertices, VertexAttributes instanceAttributes) {
        if (Kerman.gl30 == null)
            throw new KermanRuntimeException("InstanceBufferObject requires a device running with GLES 3.0 compatibilty");

        bufferHandle = Kerman.gl20.glGenBuffer();

        ByteBuffer data = BufferUtils.newUnsafeByteBuffer(instanceAttributes.vertexSize * numVertices);
        ((Buffer) data).limit(0);
        setBuffer(data, true, instanceAttributes);
        setUsage(isStatic ? GLES20.GL_STATIC_DRAW : GLES20.GL_DYNAMIC_DRAW);
    }

    @NotNull
    @Override
    public VertexAttributes getAttributes() {
        return attributes;
    }

    @Override
    public int getNumInstances() {
        return buffer.limit() * 4 / attributes.vertexSize;
    }

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

    /**
     * Low level method to reset the buffer and attributes to the specified values. Use with care!
     */
    protected void setBuffer(Buffer data, boolean ownsBuffer, VertexAttributes value) {
        if (isBound) throw new KermanRuntimeException("Cannot change attributes while VBO is bound");
        if (this.ownsBuffer && byteBuffer != null) BufferUtils.disposeUnsafeByteBuffer(byteBuffer);
        attributes = value;
        if (data instanceof ByteBuffer)
            byteBuffer = (ByteBuffer) data;
        else
            throw new KermanRuntimeException("Only ByteBuffer is currently supported");
        this.ownsBuffer = ownsBuffer;

        final int l = byteBuffer.limit();
        ((Buffer) byteBuffer).limit(byteBuffer.capacity());
        buffer = byteBuffer.asFloatBuffer();
        ((Buffer) byteBuffer).limit(l);
        ((Buffer) buffer).limit(l / 4);
    }

    private void bufferChanged() {
        if (isBound) {
            Kerman.gl20.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), null, usage);
            Kerman.gl20.glBufferData(GLES20.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
            isDirty = false;
        }
    }

    @Override
    public void setInstanceData(@NotNull float[] data, int offset, int count) {
        isDirty = true;
        BufferUtils.copy(data, byteBuffer, count, offset);
        ((Buffer) buffer).position(0);
        ((Buffer) buffer).limit(count);
        bufferChanged();
    }

    @Override
    public void setInstanceData(@NotNull FloatBuffer data, int count) {
        isDirty = true;
        BufferUtils.copy(data, byteBuffer, count);
        ((Buffer) buffer).position(0);
        ((Buffer) buffer).limit(count);
        bufferChanged();
    }

    @Override
    public void updateInstanceData(int targetOffset, @NotNull float[] data, int sourceOffset, int count) {
        isDirty = true;
        final int pos = byteBuffer.position();
        ((Buffer) byteBuffer).position(targetOffset * 4);
        BufferUtils.copy(data, sourceOffset, count, byteBuffer);
        ((Buffer) byteBuffer).position(pos);
        ((Buffer) buffer).position(0);
        bufferChanged();
    }

    @Override
    public void updateInstanceData(int targetOffset, @NotNull FloatBuffer data, int sourceOffset, int count) {
        isDirty = true;
        final int pos = byteBuffer.position();
        ((Buffer) byteBuffer).position(targetOffset * 4);
        ((Buffer) data).position(sourceOffset * 4);
        BufferUtils.copy(data, byteBuffer, count);
        ((Buffer) byteBuffer).position(pos);
        ((Buffer) buffer).position(0);
        bufferChanged();
    }

    /**
     * @return The GL enum used in the call to {@link GLES20#glBufferData(int, int, Buffer, int)}, e.g. GL_STATIC_DRAW or
     * GL_DYNAMIC_DRAW
     */
    protected int getUsage() {
        return usage;
    }

    /**
     * Set the GL enum used in the call to {@link GLES20#glBufferData(int, int, Buffer, int)}, can only be called when the
     * VBO is not bound.
     */
    protected void setUsage(int value) {
        if (isBound) throw new KermanRuntimeException("Cannot change usage while VBO is bound");
        usage = value;
    }

    /**
     * Binds this InstanceBufferObject for rendering via glDrawArraysInstanced or glDrawElementsInstanced
     *
     * @param shader the shader
     */
    @Override
    public void bind(@NotNull ShaderProgram shader) {
        bind(shader, null);
    }

    @Override
    public void bind(@NotNull ShaderProgram shader, int[] locations) {
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
                shader.disableVertexAttribute(location + unitOffset);
            }
        }
        gl.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        isBound = false;
    }

    /**
     * Invalidates the InstanceBufferObject so a new OpenGL buffer handle is created. Use this in case of a context loss.
     */
    @Override
    public void invalidate() {
        bufferHandle = Kerman.gl20.glGenBuffer();
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
        if (ownsBuffer) BufferUtils.disposeUnsafeByteBuffer(byteBuffer);
    }
}
