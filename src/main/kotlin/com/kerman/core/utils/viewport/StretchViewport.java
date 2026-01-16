package com.kerman.core.utils.viewport;

import com.kerman.core.graphics.Camera;
import com.kerman.core.graphics.OrthographicCamera;
import com.kerman.core.utils.Scaling;

/**
 * Info : This class was inspired by "com.badlogic.gdx.utils.viewport.StretchViewport".
 * <p>
 * A ScalingViewport that uses {@link Scaling#stretch} so it does not keep the aspect ratio, the world is scaled to take the
 * whole screen.
 */
public class StretchViewport extends ScalingViewport {
    /**
     * Creates a new viewport using a new {@link OrthographicCamera}.
     */
    public StretchViewport(float worldWidth, float worldHeight) {
        super(Scaling.stretch, worldWidth, worldHeight);
    }

    public StretchViewport(float worldWidth, float worldHeight, Camera camera) {
        super(Scaling.stretch, worldWidth, worldHeight, camera);
    }
}
