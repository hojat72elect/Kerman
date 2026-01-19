package com.kerman.core.graphics.k3d.utils;

import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.k3d.Renderable;
import com.kerman.core.graphics.k3d.Shader;
import com.kerman.core.graphics.k3d.shaders.DefaultShader;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider".
 */
public class DefaultShaderProvider extends BaseShaderProvider {
    public final DefaultShader.Config config;

    public DefaultShaderProvider(final DefaultShader.Config config) {
        this.config = (config == null) ? new DefaultShader.Config() : config;
    }

    public DefaultShaderProvider(final String vertexShader, final String fragmentShader) {
        this(new DefaultShader.Config(vertexShader, fragmentShader));
    }

    public DefaultShaderProvider(final FileHandle vertexShader, final FileHandle fragmentShader) {
        this(vertexShader.readString(), fragmentShader.readString());
    }

    public DefaultShaderProvider() {
        this(null);
    }

    @Override
    protected Shader createShader(final Renderable renderable) {
        return new DefaultShader(renderable, config);
    }
}
