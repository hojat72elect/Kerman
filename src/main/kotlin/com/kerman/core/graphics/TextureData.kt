package com.kerman.core.graphics

import com.kerman.core.files.FileHandle
import com.kerman.core.graphics.Pixmap.Format
import com.kerman.core.graphics.glutils.ETC1TextureData
import com.kerman.core.graphics.glutils.FileTextureData
import com.kerman.core.graphics.glutils.KTXTextureData
import com.kerman.core.graphics.glutils.MipMapGenerator

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.TextureData".
 *
 * Used by a [Texture] to load the pixel data. A TextureData can either return a [Pixmap] or upload the pixel data
 * itself. It signals its type via [getType] to the Texture that's using it. The Texture will then either invoke
 * [consumePixmap] or [consumeCustomData]. These are the first methods to be called by Texture. After that
 * the Texture will invoke the other methods to find out about the size of the image data, the format, whether mipmaps should be
 * generated and whether the TextureData is able to manage the pixel data if the OpenGL ES context is lost.
 * In case the TextureData implementation has the type [TextureDataType.Custom], the implementation has to generate the
 * mipmaps itself if necessary. See [MipMapGenerator].
 * Before a call to either [consumePixmap] or [consumeCustomData], Texture will bind the OpenGL ES texture.
 * Look at [FileTextureData] and [ETC1TextureData] for example implementations of this interface.
 */
interface TextureData {

    /**
     * @return the [TextureDataType].
     */
    fun getType(): TextureDataType

    /**
     * @return whether the TextureData is prepared or not.
     */
    fun isPrepared(): Boolean

    /**
     * Prepares the TextureData for a call to [consumePixmap] or [consumeCustomData]. This method can be
     * called from a non OpenGL thread and should thus not interact with OpenGL.
     */
    fun prepare()

    /**
     * Returns the [Pixmap] for upload by Texture. A call to [prepare] must precede a call to this method. Any
     * internal data structures created in [prepare] should be disposed of here.
     * @return the pixmap.
     */
    fun consumePixmap(): Pixmap?

    /**
     * @return whether the caller of [consumePixmap] should dispose the Pixmap returned by [consumePixmap]
     */
    fun disposePixmap(): Boolean

    /**
     * Uploads the pixel data to the OpenGL ES texture. The caller must bind an OpenGL ES texture. A call to [prepare]
     * must precede a call to this method. Any internal data structures created in [prepare] should be disposed of here.
     */
    fun consumeCustomData(target: Int)

    /**
     * @return the width of the pixel data.
     */
    fun getWidth(): Int

    /**
     * @return the height of the pixel data.
     */
    fun getHeight(): Int

    /**
     * @return the [Format] of the pixel data.
     */
    fun getFormat(): Format?

    /**
     * @return whether to generate mipmaps or not.
     */
    fun useMipMaps(): Boolean

    /**
     * @return whether this implementation can cope with a EGL context loss.
     */
    fun isManaged(): Boolean

    /**
     * The type of this [TextureData].
     */
    enum class TextureDataType {
        Pixmap, Custom
    }

    /**
     * Provides static method to instantiate the right implementation (Pixmap, ETC1, KTX).
     */
    object Factory {
        @JvmStatic
        fun loadFromFile(file: FileHandle, useMipMaps: Boolean) = loadFromFile(file, null, useMipMaps)

        @JvmStatic
        fun loadFromFile(file: FileHandle?, format: Format?, useMipMaps: Boolean): TextureData? {
            if (file == null) return null
            if (file.name().endsWith(".cim")) return FileTextureData(file, PixmapIO.readCIM(file), format, useMipMaps)
            if (file.name().endsWith(".etc1")) return ETC1TextureData(file, useMipMaps)
            if (file.name().endsWith(".ktx") || file.name().endsWith(".zktx")) return KTXTextureData(file, useMipMaps)
            return FileTextureData(file, Pixmap(file), format, useMipMaps)
        }
    }
}
