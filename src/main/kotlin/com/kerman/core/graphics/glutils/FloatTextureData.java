package com.kerman.core.graphics.glutils;

import com.kerman.core.Application.ApplicationType;
import com.kerman.core.Kerman;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.GLES30;
import com.kerman.core.graphics.Pixmap;
import com.kerman.core.graphics.Pixmap.Format;
import com.kerman.core.graphics.TextureData;
import com.kerman.core.utils.BufferUtils;
import com.kerman.core.utils.KermanRuntimeException;

import java.nio.FloatBuffer;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.glutils.FloatTextureData".
 * <p>
 * A {@link TextureData} implementation which should be used to create float textures.
 */
public class FloatTextureData implements TextureData {

    int width = 0;
    int height = 0;

    int internalFormat;
    int format;
    int type;

    boolean isGpuOnly;

    boolean isPrepared = false;
    FloatBuffer buffer;

    public FloatTextureData(int w, int h, int internalFormat, int format, int type, boolean isGpuOnly) {
        this.width = w;
        this.height = h;
        this.internalFormat = internalFormat;
        this.format = format;
        this.type = type;
        this.isGpuOnly = isGpuOnly;
    }

    @Override
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    @Override
    public boolean isPrepared() {
        return isPrepared;
    }

    @Override
    public void prepare() {
        if (isPrepared) throw new KermanRuntimeException("Already prepared");
        if (!isGpuOnly) {
            int amountOfFloats = 4;
            if (Kerman.graphics.getGLVersion().getType().equals(GLVersion.Type.OpenGL)) {
                if (internalFormat == GLES30.GL_RGBA16F || internalFormat == GLES30.GL_RGBA32F) amountOfFloats = 4;
                if (internalFormat == GLES30.GL_RGB16F || internalFormat == GLES30.GL_RGB32F) amountOfFloats = 3;
                if (internalFormat == GLES30.GL_RG16F || internalFormat == GLES30.GL_RG32F) amountOfFloats = 2;
                if (internalFormat == GLES30.GL_R16F || internalFormat == GLES30.GL_R32F) amountOfFloats = 1;
            }
            this.buffer = BufferUtils.newFloatBuffer(width * height * amountOfFloats);
        }
        isPrepared = true;
    }

    @Override
    public void consumeCustomData(int target) {
        if (Kerman.app.getType() == ApplicationType.Android || Kerman.app.getType() == ApplicationType.iOS
                || (Kerman.app.getType() == ApplicationType.WebGL && !Kerman.graphics.isGL30Available())) {

            if (!Kerman.graphics.supportsExtension("OES_texture_float"))
                throw new KermanRuntimeException("Extension OES_texture_float not supported!");

            // GLES and WebGL defines texture format by 3rd and 8th argument,
            // so to get a float texture one needs to supply GL_RGBA and GL_FLOAT there.
            Kerman.gl.glTexImage2D(target, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_FLOAT, buffer);
        } else {
            if (!Kerman.graphics.isGL30Available()) {
                if (!Kerman.graphics.supportsExtension("GL_ARB_texture_float"))
                    throw new KermanRuntimeException("Extension GL_ARB_texture_float not supported!");
            }
            // in desktop OpenGL the texture format is defined only by the third argument,
            // hence we need to use GL_RGBA32F there (this constant is unavailable in GLES/WebGL)
            Kerman.gl.glTexImage2D(target, 0, internalFormat, width, height, 0, format, GLES20.GL_FLOAT, buffer);
        }
    }

    @Override
    public Pixmap consumePixmap() {
        throw new KermanRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public boolean disposePixmap() {
        throw new KermanRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Format getFormat() {
        return Format.RGBA8888; // it's not true, but FloatTextureData.getFormat() isn't used anywhere
    }

    @Override
    public boolean useMipMaps() {
        return false;
    }

    @Override
    public boolean isManaged() {
        return true;
    }

    public FloatBuffer getBuffer() {
        return buffer;
    }
}
