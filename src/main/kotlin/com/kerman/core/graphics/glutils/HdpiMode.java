package com.kerman.core.graphics.glutils;

import com.kerman.core.Graphics;
import com.kerman.core.graphics.GLES20;

/**
 * This enum was inspired by "com.badlogic.gdx.graphics.glutils.HdpiMode".
 */
public enum HdpiMode {
    /**
     * mouse coordinates, {@link Graphics#getWidth()} and {@link Graphics#getHeight()} will return logical coordinates according
     * to the system defined HDPI scaling. Rendering will be performed to a backbuffer at raw resolution. Use {@link HdpiUtils}
     * when calling {@link GLES20#glScissor} or {@link GLES20#glViewport} which expect raw coordinates.
     */
    Logical,

    /**
     * Mouse coordinates, {@link Graphics#getWidth()} and {@link Graphics#getHeight()} will return raw pixel coordinates
     * irrespective of the system defined HDPI scaling.
     */
    Pixels
}
