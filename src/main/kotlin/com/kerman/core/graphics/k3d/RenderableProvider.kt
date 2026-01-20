package com.kerman.core.graphics.k3d

import com.kerman.core.utils.KermanArray
import com.kerman.core.utils.Pool

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.g3d.RenderableProvider".
 *
 * Returns a list of [Renderable] instances to be rendered by a [ModelBatch].
 */
interface RenderableProvider {
    /**
     * Returns [Renderable] instances. Renderables are obtained from the provided [Pool] and added to the provided
     * array. The Renderables obtained using [Pool.obtain] will later be put back into the pool, do not store them
     * internally. The resulting array can be rendered via a [ModelBatch].
     *
     * @param renderables the output array.
     * @param pool the pool to obtain Renderables from.
     */
    fun getRenderables(renderables: KermanArray<Renderable>, pool: Pool<Renderable>)
}
