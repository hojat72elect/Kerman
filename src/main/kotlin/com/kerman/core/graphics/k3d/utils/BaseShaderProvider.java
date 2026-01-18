package com.kerman.core.graphics.k3d.utils;

import com.kerman.core.graphics.k3d.Renderable;
import com.kerman.core.graphics.k3d.Shader;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.KermanRuntimeException;
import org.jetbrains.annotations.NotNull;

/**
 * Info : This abstract class was inspired by "com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider".
 */
public abstract class BaseShaderProvider implements ShaderProvider {
    protected KermanArray<Shader> shaders = new KermanArray<>();

    @NotNull
    @Override
    public Shader getShader(Renderable renderable) {
        Shader suggestedShader = renderable.shader;
        if (suggestedShader != null && suggestedShader.canRender(renderable)) return suggestedShader;
        for (Shader shader : shaders) {
            if (shader.canRender(renderable)) return shader;
        }
        final Shader shader = createShader(renderable);
        if (!shader.canRender(renderable)) throw new KermanRuntimeException("unable to provide a shader for this renderable");
        shader.init();
        shaders.add(shader);
        return shader;
    }

    protected abstract Shader createShader(final Renderable renderable);

    @Override
    public void dispose() {
        for (Shader shader : shaders) {
            shader.dispose();
        }
        shaders.clear();
    }
}
