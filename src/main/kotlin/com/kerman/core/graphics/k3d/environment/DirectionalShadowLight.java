package com.kerman.core.graphics.k3d.environment;

import com.kerman.core.Kerman;
import com.kerman.core.graphics.Camera;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.OrthographicCamera;
import com.kerman.core.graphics.Pixmap.Format;
import com.kerman.core.graphics.Texture;
import com.kerman.core.graphics.k3d.utils.TextureDescriptor;
import com.kerman.core.graphics.glutils.FrameBuffer;
import com.kerman.core.math.Matrix4;
import com.kerman.core.math.Vector3;
import com.kerman.core.utils.Disposable;

import org.jetbrains.annotations.NotNull;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight".
 */
public class DirectionalShadowLight extends DirectionalLight implements ShadowMap, Disposable {
    protected final Vector3 tmpV = new Vector3();
    protected final TextureDescriptor textureDesc;
    protected FrameBuffer fbo;
    protected Camera cam;
    protected float halfDepth;
    protected float halfHeight;

    public DirectionalShadowLight(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight,
                                  float shadowNear, float shadowFar) {
        fbo = new FrameBuffer(Format.RGBA8888, shadowMapWidth, shadowMapHeight, true);
        cam = new OrthographicCamera(shadowViewportWidth, shadowViewportHeight);
        cam.near = shadowNear;
        cam.far = shadowFar;
        halfHeight = shadowViewportHeight * 0.5f;
        halfDepth = shadowNear + 0.5f * (shadowFar - shadowNear);
        textureDesc = new TextureDescriptor();
        textureDesc.minFilter = textureDesc.magFilter = Texture.TextureFilter.Nearest;
        textureDesc.uWrap = textureDesc.vWrap = Texture.TextureWrap.ClampToEdge;
    }

    public void update(final Camera camera) {
        update(tmpV.set(camera.direction).scl(halfHeight), camera.direction);
    }

    public void update(final Vector3 center, final Vector3 forward) {
        cam.position.set(direction).scl(-halfDepth).add(center);
        cam.direction.set(direction).nor();
        cam.normalizeUp();
        cam.update();
    }

    public void begin(final Camera camera) {
        update(camera);
        begin();
    }

    public void begin(final Vector3 center, final Vector3 forward) {
        update(center, forward);
        begin();
    }

    public void begin() {
        final int w = fbo.getWidth();
        final int h = fbo.getHeight();
        fbo.begin();
        Kerman.gl.glViewport(0, 0, w, h);
        Kerman.gl.glClearColor(1, 1, 1, 1);
        Kerman.gl.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Kerman.gl.glEnable(GLES20.GL_SCISSOR_TEST);
        Kerman.gl.glScissor(1, 1, w - 2, h - 2);
    }

    public void end() {
        Kerman.gl.glDisable(GLES20.GL_SCISSOR_TEST);
        fbo.end();
    }

    public FrameBuffer getFrameBuffer() {
        return fbo;
    }

    public Camera getCamera() {
        return cam;
    }

    @NotNull
    @Override
    public Matrix4 getProjViewTrans() {
        return cam.combined;
    }

    @NotNull
    @Override
    public TextureDescriptor getDepthMap() {
        textureDesc.texture = fbo.getColorBufferTexture();
        return textureDesc;
    }

    @Override
    public void dispose() {
        if (fbo != null) fbo.dispose();
        fbo = null;
    }
}
