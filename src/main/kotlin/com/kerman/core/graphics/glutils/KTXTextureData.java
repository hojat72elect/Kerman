package com.kerman.core.graphics.glutils;

import com.kerman.core.Kerman;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Cubemap;
import com.kerman.core.graphics.CubemapData;
import com.kerman.core.graphics.Pixmap;
import com.kerman.core.graphics.Pixmap.Format;
import com.kerman.core.graphics.Texture;
import com.kerman.core.graphics.TextureData;
import com.kerman.core.graphics.glutils.ETC1.ETC1Data;
import com.kerman.core.utils.BufferUtils;
import com.kerman.core.utils.StreamUtils;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.utils.KermanRuntimeException;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.zip.GZIPInputStream;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.glutils.KTXTextureData".
 * <p>
 * A KTXTextureData holds the data from a KTX (or zipped KTX file, aka ZKTX). That is to say an OpenGL ready texture data. The
 * KTX file format is just a thin wrapper around OpenGL textures and therefore is compatible with most OpenGL texture capabilities
 * like texture compression, cubemapping, mipmapping, etc.
 * <p>
 * For example, KTXTextureData can be used for {@link Texture} or {@link Cubemap}.
 */
public class KTXTextureData implements TextureData, CubemapData {

    private static final int GL_TEXTURE_1D = 0x1234;
    private static final int GL_TEXTURE_3D = 0x1234;
    private static final int GL_TEXTURE_1D_ARRAY_EXT = 0x1234;
    private static final int GL_TEXTURE_2D_ARRAY_EXT = 0x1234;
    // The file we are loading
    private final FileHandle file;
    // KTX header (only available after preparing)
    private int glType;
    private int glTypeSize;
    private int glFormat;
    private int glInternalFormat;
    private int glBaseInternalFormat;
    private int pixelWidth = -1;
    private int pixelHeight = -1;
    private int pixelDepth = -1;
    private int numberOfArrayElements;
    private int numberOfFaces;
    private int numberOfMipmapLevels;
    private int imagePos;
    // KTX image data (only available after preparing and before consuming)
    private ByteBuffer compressedData;
    // Whether to generate mipmaps if they are not included in the file
    private boolean useMipMaps;

    public KTXTextureData(FileHandle file, boolean genMipMaps) {
        this.file = file;
        this.useMipMaps = genMipMaps;
    }

    @NotNull
    @Override
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    @Override
    public boolean isPrepared() {
        return compressedData != null;
    }

    @Override
    public void prepare() {
        if (compressedData != null) throw new KermanRuntimeException("Already prepared");
        if (file == null) throw new KermanRuntimeException("Need a file to load from");
        // We support normal ktx files as well as 'zktx' which are gzip ktx file with an int length at the beginning (like ETC1).
        if (file.name().endsWith(".zktx")) {
            byte[] buffer = new byte[1024 * 10];
            DataInputStream in = null;
            try {
                in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(file.read())));
                int fileSize = in.readInt();
                compressedData = BufferUtils.newUnsafeByteBuffer(fileSize);
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    compressedData.put(buffer, 0, readBytes);
                ((Buffer) compressedData).position(0);
                ((Buffer) compressedData).limit(compressedData.capacity());
            } catch (Exception e) {
                throw new KermanRuntimeException("Couldn't load zktx file '" + file + "'", e);
            } finally {
                StreamUtils.closeQuietly(in);
            }
        } else {
            compressedData = ByteBuffer.wrap(file.readBytes());
        }
        if (compressedData.get() != (byte) 0x0AB) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x04B) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x054) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x058) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x020) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x031) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x031) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x0BB) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x00D) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x00A) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x01A) throw new KermanRuntimeException("Invalid KTX Header");
        if (compressedData.get() != (byte) 0x00A) throw new KermanRuntimeException("Invalid KTX Header");
        int endianTag = compressedData.getInt();
        if (endianTag != 0x04030201 && endianTag != 0x01020304) throw new KermanRuntimeException("Invalid KTX Header");
        if (endianTag != 0x04030201)
            compressedData.order(compressedData.order() == ByteOrder.BIG_ENDIAN ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        glType = compressedData.getInt();
        glTypeSize = compressedData.getInt();
        glFormat = compressedData.getInt();
        glInternalFormat = compressedData.getInt();
        glBaseInternalFormat = compressedData.getInt();
        pixelWidth = compressedData.getInt();
        pixelHeight = compressedData.getInt();
        pixelDepth = compressedData.getInt();
        numberOfArrayElements = compressedData.getInt();
        numberOfFaces = compressedData.getInt();
        numberOfMipmapLevels = compressedData.getInt();
        if (numberOfMipmapLevels == 0) {
            numberOfMipmapLevels = 1;
            useMipMaps = true;
        }
        int bytesOfKeyValueData = compressedData.getInt();
        imagePos = compressedData.position() + bytesOfKeyValueData;
        if (!compressedData.isDirect()) {
            int pos = imagePos;
            for (int level = 0; level < numberOfMipmapLevels; level++) {
                int faceLodSize = compressedData.getInt(pos);
                int faceLodSizeRounded = (faceLodSize + 3) & ~3;
                pos += faceLodSizeRounded * numberOfFaces + 4;
            }
            ((Buffer) compressedData).limit(pos);
            ((Buffer) compressedData).position(0);
            ByteBuffer directBuffer = BufferUtils.newUnsafeByteBuffer(pos);
            directBuffer.order(compressedData.order());
            directBuffer.put(compressedData);
            compressedData = directBuffer;
        }
    }

    @Override
    public void consumeCubemapData() {
        consumeCustomData(GLES20.GL_TEXTURE_CUBE_MAP);
    }

    @Override
    public void consumeCustomData(int target) {
        if (compressedData == null) throw new KermanRuntimeException("Call prepare() before calling consumeCompressedData()");
        IntBuffer buffer = BufferUtils.newIntBuffer(16);

        // Check OpenGL type and format, detect compressed data format (no type & format)
        boolean compressed = false;
        if (glType == 0 || glFormat == 0) {
            if (glType + glFormat != 0) throw new KermanRuntimeException("either both or none of glType, glFormat must be zero");
            compressed = true;
        }

        // find OpenGL texture target and dimensions
        int textureDimensions = 1;
        int glTarget = GL_TEXTURE_1D;
        if (pixelHeight > 0) {
            textureDimensions = 2;
            glTarget = GLES20.GL_TEXTURE_2D;
        }
        if (pixelDepth > 0) {
            textureDimensions = 3;
            glTarget = GL_TEXTURE_3D;
        }
        if (numberOfFaces == 6) {
            if (textureDimensions == 2)
                glTarget = GLES20.GL_TEXTURE_CUBE_MAP;
            else
                throw new KermanRuntimeException("cube map needs 2D faces");
        } else if (numberOfFaces != 1) {
            throw new KermanRuntimeException("numberOfFaces must be either 1 or 6");
        }
        if (numberOfArrayElements > 0) {
            if (glTarget == GL_TEXTURE_1D)
                glTarget = GL_TEXTURE_1D_ARRAY_EXT;
            else if (glTarget == GLES20.GL_TEXTURE_2D)
                glTarget = GL_TEXTURE_2D_ARRAY_EXT;
            else
                throw new KermanRuntimeException("No API for 3D and cube arrays yet");
            textureDimensions++;
        }
        if (glTarget == 0x1234)
            throw new KermanRuntimeException("Unsupported texture format (only 2D texture are supported in LibGdx for the time being)");

        int singleFace = -1;
        if (numberOfFaces == 6 && target != GLES20.GL_TEXTURE_CUBE_MAP) {
            // Load a single face of the cube (should be avoided since the data is unloaded afterwards)
            if (!(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X <= target && target <= GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z))
                throw new KermanRuntimeException(
                        "You must specify either GL_TEXTURE_CUBE_MAP to bind all 6 faces of the cube or the requested face GL_TEXTURE_CUBE_MAP_POSITIVE_X and followings.");
            singleFace = target - GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
            target = GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
        } else if (numberOfFaces == 6 && target == GLES20.GL_TEXTURE_CUBE_MAP) {
            // Load the 6 faces
            target = GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
        } else {
            // Load normal texture
            if (target != glTarget && !(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X <= target
                    && target <= GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z && target == GLES20.GL_TEXTURE_2D))
                throw new KermanRuntimeException("Invalid target requested : 0x" + Integer.toHexString(target) + ", expecting : 0x"
                        + Integer.toHexString(glTarget));
        }

        // KTX files require an unpack alignment of 4
        Kerman.gl.glGetIntegerv(GLES20.GL_UNPACK_ALIGNMENT, buffer);
        int previousUnpackAlignment = buffer.get(0);
        if (previousUnpackAlignment != 4) Kerman.gl.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 4);
        int glInternalFormat = this.glInternalFormat;
        int glFormat = this.glFormat;
        int pos = imagePos;
        for (int level = 0; level < numberOfMipmapLevels; level++) {
            int pixelWidth = Math.max(1, this.pixelWidth >> level);
            int pixelHeight = Math.max(1, this.pixelHeight >> level);
            int pixelDepth = Math.max(1, this.pixelDepth >> level);
            ((Buffer) compressedData).position(pos);
            int faceLodSize = compressedData.getInt();
            int faceLodSizeRounded = (faceLodSize + 3) & ~3;
            pos += 4;
            for (int face = 0; face < numberOfFaces; face++) {
                ((Buffer) compressedData).position(pos);
                pos += faceLodSizeRounded;
                if (singleFace != -1 && singleFace != face) continue;
                ByteBuffer data = compressedData.slice();
                ((Buffer) data).limit(faceLodSizeRounded);
                if (textureDimensions == 2) {
                    if (numberOfArrayElements > 0) pixelHeight = numberOfArrayElements;
                    if (compressed) {
                        if (glInternalFormat == ETC1.ETC1_RGB8_OES) {
                            if (!Kerman.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
                                ETC1Data etcData = new ETC1Data(pixelWidth, pixelHeight, data, 0);
                                Pixmap pixmap = ETC1.decodeImage(etcData, Format.RGB888);
                                Kerman.gl.glTexImage2D(target + face, level, pixmap.getGLInternalFormat(), pixmap.getWidth(),
                                        pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
                                pixmap.dispose();
                            } else {
                                Kerman.gl.glCompressedTexImage2D(target + face, level, glInternalFormat, pixelWidth, pixelHeight, 0,
                                        faceLodSize, data);
                            }
                        } else {
                            // Try to load (no software unpacking fallback)
                            Kerman.gl.glCompressedTexImage2D(target + face, level, glInternalFormat, pixelWidth, pixelHeight, 0,
                                    faceLodSize, data);
                        }
                    } else
                        Kerman.gl.glTexImage2D(target + face, level, glInternalFormat, pixelWidth, pixelHeight, 0, glFormat, glType, data);
                } else if (textureDimensions == 3) {
                    if (numberOfArrayElements > 0) pixelDepth = numberOfArrayElements;
                }
            }
        }
        if (previousUnpackAlignment != 4) Kerman.gl.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, previousUnpackAlignment);
        if (useMipMaps()) Kerman.gl.glGenerateMipmap(target);

        // dispose data once transferred to GPU
        disposePreparedData();
    }

    public void disposePreparedData() {
        if (compressedData != null) BufferUtils.disposeUnsafeByteBuffer(compressedData);
        compressedData = null;
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
        return pixelWidth;
    }

    @Override
    public int getHeight() {
        return pixelHeight;
    }

    public int getNumberOfMipMapLevels() {
        return numberOfMipmapLevels;
    }

    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    public int getGlInternalFormat() {
        return glInternalFormat;
    }

    public ByteBuffer getData(int requestedLevel, int requestedFace) {
        int pos = imagePos;
        for (int level = 0; level < numberOfMipmapLevels; level++) {
            int faceLodSize = compressedData.getInt(pos);
            int faceLodSizeRounded = (faceLodSize + 3) & ~3;
            pos += 4;
            if (level == requestedLevel) {
                for (int face = 0; face < numberOfFaces; face++) {
                    if (face == requestedFace) {
                        ((Buffer) compressedData).position(pos);
                        ByteBuffer data = compressedData.slice();
                        ((Buffer) data).limit(faceLodSizeRounded);
                        return data;
                    }
                    pos += faceLodSizeRounded;
                }
            } else {
                pos += faceLodSizeRounded * numberOfFaces;
            }
        }
        return null;
    }

    @Override
    public Format getFormat() {
        throw new KermanRuntimeException("This TextureData implementation directly handles texture formats.");
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
