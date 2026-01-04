package com.kerman.core.graphics.glutils;

import com.kerman.core.Kerman;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.Pixmap;
import com.kerman.core.graphics.Pixmap.Format;
import com.kerman.core.graphics.TextureData;
import com.kerman.core.graphics.glutils.ETC1.ETC1Data;
import com.kerman.core.utils.KermanRuntimeException;
import org.jetbrains.annotations.NotNull;

/**
 * This class was inspired by "com.badlogic.gdx.graphics.glutils.ETC1TextureData".
 */
public class ETC1TextureData implements TextureData {
    FileHandle file;
    ETC1Data data;
    boolean useMipMaps;
    int width = 0;
    int height = 0;
    boolean isPrepared = false;

    public ETC1TextureData(FileHandle file) {
        this(file, false);
    }

    public ETC1TextureData(FileHandle file, boolean useMipMaps) {
        this.file = file;
        this.useMipMaps = useMipMaps;
    }

    public ETC1TextureData(ETC1Data encodedImage, boolean useMipMaps) {
        this.data = encodedImage;
        this.useMipMaps = useMipMaps;
    }

    @Override
    @NotNull
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
        if (file == null && data == null) throw new KermanRuntimeException("Can only load once from ETC1Data");
        if (file != null) {
            data = new ETC1Data(file);
        }
        width = data.width;
        height = data.height;
        isPrepared = true;
    }

    @Override
    public void consumeCustomData(int target) {
        if (!isPrepared) throw new KermanRuntimeException("Call prepare() before calling consumeCompressedData()");

        if (!Kerman.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
            Pixmap pixmap = ETC1.decodeImage(data, Format.RGB565);
            Kerman.gl.glTexImage2D(target, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0,
                    pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            if (useMipMaps) MipMapGenerator.generateMipMap(target, pixmap, pixmap.getWidth(), pixmap.getHeight());
            pixmap.dispose();
            useMipMaps = false;
        } else {
            Kerman.gl.glCompressedTexImage2D(target, 0, ETC1.ETC1_RGB8_OES, width, height, 0,
                    data.compressedData.capacity() - data.dataOffset, data.compressedData);
            if (useMipMaps()) Kerman.gl20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        }
        data.dispose();
        data = null;
        isPrepared = false;
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
        return Format.RGB565;
    }

    @Override
    public boolean useMipMaps() {
        return useMipMaps;
    }

    @Override
    public boolean isManaged() {
        return true;
    }
}
