package com.kerman.core.graphics.k3d.particles.renderers;

import com.kerman.core.graphics.k3d.particles.ParallelArray.FloatChannel;
import com.kerman.core.graphics.k3d.particles.ParticleController;

/**
 * Info : This abstract class was inspired by "com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData".
 * <p>
 * Render data used by particle controller renderer
 */
public abstract class ParticleControllerRenderData {
    public ParticleController controller;
    public FloatChannel positionChannel;
}
