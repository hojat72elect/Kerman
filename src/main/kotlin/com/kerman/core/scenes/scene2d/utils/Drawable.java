package com.kerman.core.scenes.scene2d.utils;

import com.kerman.core.graphics.k2d.Batch;

/**
 * Info : This interface was inspired by "com.badlogic.gdx.scenes.scene2d.utils.Drawable".
 * <p>
 * A drawable knows how to draw itself at a given rectangular size. It provides padding sizes and a minimum size so that other
 * code can determine how to size and position content.
 * <p/>
 * WARNING : As long as those classes which implement this interface are still in Java, do not convert this interface to kotlin. You will face weird errors
 * because of those "default" functions in here.
 */
public interface Drawable {
    /**
     * Draws this drawable at the specified bounds. The drawable should be tinted with {@link Batch#getColor()}, possibly by
     * mixing its own color.
     */
    public void draw(Batch batch, float x, float y, float width, float height);

    public float getLeftWidth();

    public void setLeftWidth(float leftWidth);

    public float getRightWidth();

    public void setRightWidth(float rightWidth);

    public float getTopHeight();

    public void setTopHeight(float topHeight);

    public float getBottomHeight();

    public void setBottomHeight(float bottomHeight);

    default public void setPadding(float topHeight, float leftWidth, float bottomHeight, float rightWidth) {
        setTopHeight(topHeight);
        setLeftWidth(leftWidth);
        setBottomHeight(bottomHeight);
        setRightWidth(rightWidth);
    }

    default public void setPadding(float padding) {
        setPadding(padding, padding, padding, padding);
    }

    default public void setPadding(Drawable from) {
        setPadding(from.getTopHeight(), from.getLeftWidth(), from.getBottomHeight(), from.getRightWidth());
    }

    public float getMinWidth();

    public void setMinWidth(float minWidth);

    public float getMinHeight();

    public void setMinHeight(float minHeight);

    default public void setMinSize(float minWidth, float minHeight) {
        setMinWidth(minWidth);
        setMinHeight(minHeight);
    }
}