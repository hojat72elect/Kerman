package com.kerman.core.graphics.k3d.particles.batches

import com.kerman.core.assets.AssetManager
import com.kerman.core.graphics.k3d.RenderableProvider
import com.kerman.core.graphics.k3d.particles.ResourceData
import com.kerman.core.graphics.k3d.particles.ResourceData.Configurable
import com.kerman.core.graphics.k3d.particles.renderers.ParticleControllerRenderData

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch".
 *
 * Common interface to all the batches that render particles.
 */
interface ParticleBatch<T : ParticleControllerRenderData> : RenderableProvider, Configurable<Any> {

    /**
     * Must be called once before any drawing operation
     */
    fun begin()

    fun draw(controller: T)

    /**
     * Must be called after all the drawing operations
     */
    fun end()

    override fun save(manager: AssetManager, resources: ResourceData<Any>)

    override fun load(manager: AssetManager, resources: ResourceData<Any>)
}
