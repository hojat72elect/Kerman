package com.kerman.core.graphics.k3d.utils;

import com.kerman.core.Kerman;
import com.kerman.core.graphics.GLES20;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.utils.RenderContext".
 * <p>
 * Manages OpenGL state and tries to reduce state changes. Uses a {@link TextureBinder} to reduce texture binds as well. Call
 * {@link #begin()} to setup the context, call {@link #end()} to undo all state changes. Use the setters to change state, use
 * {@link #textureBinder} to bind textures.
 */
public class RenderContext {
    /**
     * used to bind textures
     **/
    public final TextureBinder textureBinder;
    private boolean blending;
    private int blendSourceRgbFactor;
    private int blendDestRgbFactor;
    private int blendSourceAlphaFactor;
    private int blendDestAlphaFactor;
    private int depthFunc;
    private float depthRangeNear;
    private float depthRangeFar;
    private boolean depthMask;
    private int cullFace;

    public RenderContext(TextureBinder textures) {
        this.textureBinder = textures;
    }

    /**
     * Sets up the render context, must be matched with a call to {@link #end()}.
     */
    public void begin() {
        Kerman.gl.glDisable(GLES20.GL_DEPTH_TEST);
        depthFunc = 0;
        Kerman.gl.glDepthMask(true);
        depthMask = true;
        Kerman.gl.glDisable(GLES20.GL_BLEND);
        blending = false;
        Kerman.gl.glDisable(GLES20.GL_CULL_FACE);
        cullFace = blendSourceRgbFactor = blendDestRgbFactor = blendSourceAlphaFactor = blendDestAlphaFactor = 0;
        textureBinder.begin();
    }

    /**
     * Resets all changed OpenGL states to their defaults.
     */
    public void end() {
        if (depthFunc != 0) Kerman.gl.glDisable(GLES20.GL_DEPTH_TEST);
        if (!depthMask) Kerman.gl.glDepthMask(true);
        if (blending) Kerman.gl.glDisable(GLES20.GL_BLEND);
        if (cullFace > 0) Kerman.gl.glDisable(GLES20.GL_CULL_FACE);
        textureBinder.end();
    }

    public void setDepthMask(final boolean depthMask) {
        if (this.depthMask != depthMask) Kerman.gl.glDepthMask(this.depthMask = depthMask);
    }

    public void setDepthTest(final int depthFunction) {
        setDepthTest(depthFunction, 0f, 1f);
    }

    public void setDepthTest(final int depthFunction, final float depthRangeNear, final float depthRangeFar) {
        final boolean wasEnabled = depthFunc != 0;
        final boolean enabled = depthFunction != 0;
        if (depthFunc != depthFunction) {
            depthFunc = depthFunction;
            if (enabled) {
                Kerman.gl.glEnable(GLES20.GL_DEPTH_TEST);
                Kerman.gl.glDepthFunc(depthFunction);
            } else
                Kerman.gl.glDisable(GLES20.GL_DEPTH_TEST);
        }
        if (enabled) {
            if (!wasEnabled || depthFunc != depthFunction) Kerman.gl.glDepthFunc(depthFunc = depthFunction);
            if (!wasEnabled || this.depthRangeNear != depthRangeNear || this.depthRangeFar != depthRangeFar)
                Kerman.gl.glDepthRangef(this.depthRangeNear = depthRangeNear, this.depthRangeFar = depthRangeFar);
        }
    }

    public void setBlending(final boolean enabled, final int sFactor, final int dFactor) {
        setBlending(enabled, sFactor, dFactor, sFactor, dFactor);
    }

    public void setBlending(final boolean enabled, final int sRgbFactor, final int dRgbFactor, final int sAlphaFactor,
                            final int dAlphaFactor) {
        if (enabled != blending) {
            blending = enabled;
            if (enabled)
                Kerman.gl.glEnable(GLES20.GL_BLEND);
            else
                Kerman.gl.glDisable(GLES20.GL_BLEND);
        }
        if (enabled && (blendSourceRgbFactor != sRgbFactor || blendDestRgbFactor != dRgbFactor
                || blendSourceAlphaFactor != sAlphaFactor || blendDestAlphaFactor != dAlphaFactor)) {
            Kerman.gl.glBlendFuncSeparate(sRgbFactor, dRgbFactor, sAlphaFactor, dAlphaFactor);
            blendSourceRgbFactor = sRgbFactor;
            blendDestRgbFactor = dRgbFactor;
            blendSourceAlphaFactor = sAlphaFactor;
            blendDestAlphaFactor = dAlphaFactor;
        }
    }

    public void setCullFace(final int face) {
        if (face != cullFace) {
            cullFace = face;
            if ((face == GLES20.GL_FRONT) || (face == GLES20.GL_BACK) || (face == GLES20.GL_FRONT_AND_BACK)) {
                Kerman.gl.glEnable(GLES20.GL_CULL_FACE);
                Kerman.gl.glCullFace(face);
            } else
                Kerman.gl.glDisable(GLES20.GL_CULL_FACE);
        }
    }
}
