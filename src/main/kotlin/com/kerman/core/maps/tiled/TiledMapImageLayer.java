package com.kerman.core.maps.tiled;

import com.kerman.core.graphics.Pixmap;
import com.kerman.core.graphics.k2d.TextureRegion;
import com.kerman.core.maps.MapLayer;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.TiledMapImageLayer".
 */
public class TiledMapImageLayer extends MapLayer {

    private TextureRegion region;

    private float x;
    private float y;
    private boolean repeatX;
    private boolean repeatY;
    private final boolean supportsTransparency;

    public TiledMapImageLayer(TextureRegion region, float x, float y, boolean repeatX, boolean repeatY) {
        this.region = region;
        this.x = x;
        this.y = y;
        this.repeatX = repeatX;
        this.repeatY = repeatY;
        this.supportsTransparency = checkTransparencySupport(region);
    }

    /**
     * TiledMap ImageLayers can support transparency through tint color if the image provided supports the proper pixel format.
     * Here we check to see if the file supports transparency by checking the format of the TextureData.
     *
     * @param region TextureRegion of the ImageLayer
     * @return boolean
     */
    private boolean checkTransparencySupport(TextureRegion region) {
        Pixmap.Format format = region.getTexture().getTextureData().getFormat();
        return format != null && formatHasAlpha(format);
    }

    // Check if pixel format supports alpha channel
    private boolean formatHasAlpha(Pixmap.Format format) {
        switch (format) {
            case Alpha:
            case LuminanceAlpha:
            case RGBA4444:
            case RGBA8888:
                return true;
            default:
                return false;
        }
    }

    public boolean supportsTransparency() {
        return supportsTransparency;
    }

    public TextureRegion getTextureRegion() {
        return region;
    }

    public void setTextureRegion(TextureRegion region) {
        this.region = region;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isRepeatX() {
        return repeatX;
    }

    public void setRepeatX(boolean repeatX) {
        this.repeatX = repeatX;
    }

    public boolean isRepeatY() {
        return repeatY;
    }

    public void setRepeatY(boolean repeatY) {
        this.repeatY = repeatY;
    }
}
