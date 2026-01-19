package com.kerman.core.graphics.k3d.shaders;

import com.kerman.core.Kerman;
import com.kerman.core.graphics.Camera;
import com.kerman.core.graphics.GLES20;
import com.kerman.core.graphics.k3d.Attributes;
import com.kerman.core.graphics.k3d.Renderable;
import com.kerman.core.graphics.k3d.attributes.BlendingAttribute;
import com.kerman.core.graphics.k3d.attributes.FloatAttribute;
import com.kerman.core.graphics.k3d.attributes.TextureAttribute;
import com.kerman.core.graphics.k3d.utils.RenderContext;
import com.kerman.core.graphics.glutils.ShaderProgram;
import com.kerman.core.utils.KermanRuntimeException;
import org.jetbrains.annotations.NotNull;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.shaders.DepthShader".
 */
public class DepthShader extends DefaultShader {
    private final static Attributes tmpAttributes = new Attributes();
    private static String defaultVertexShader = null;
    private static String defaultFragmentShader = null;
    public final int numBones;
    private final FloatAttribute alphaTestAttribute;

    public DepthShader(final Renderable renderable) {
        this(renderable, new Config());
    }

    public DepthShader(final Renderable renderable, final Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }
    public DepthShader(final Renderable renderable, final Config config, final String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(),
                config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DepthShader(final Renderable renderable, final Config config, final String prefix, final String vertexShader,
                       final String fragmentShader) {
        this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
    }

    public DepthShader(final Renderable renderable, final Config config, final ShaderProgram shaderProgram) {
        super(renderable, config, shaderProgram);
        final Attributes attributes = combineAttributes(renderable);

        if (renderable.bones != null && renderable.bones.length > config.numBones) {
            throw new KermanRuntimeException("too many bones: " + renderable.bones.length + ", max configured: " + config.numBones);
        }

        this.numBones = renderable.bones == null ? 0 : config.numBones;
        int boneWeights = renderable.meshPart.mesh.getVertexAttributes().getBoneWeights();
        if (boneWeights > config.numBoneWeights) {
            throw new KermanRuntimeException("too many bone weights: " + boneWeights + ", max configured: " + config.numBoneWeights);
        }
        alphaTestAttribute = new FloatAttribute(FloatAttribute.AlphaTest, config.defaultAlphaTest);
    }

    public final static String getDefaultVertexShader() {
        if (defaultVertexShader == null)
            defaultVertexShader = Kerman.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.vertex.glsl").readString();
        return defaultVertexShader;
    }

    public final static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null)
            defaultFragmentShader = Kerman.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.fragment.glsl").readString();
        return defaultFragmentShader;
    }

    public static String createPrefix(final Renderable renderable, final Config config) {
        String prefix = DefaultShader.createPrefix(renderable, config);
        if (!config.depthBufferOnly) prefix += "#define PackedDepthFlag\n";
        return prefix;
    }

    // TODO: Move responsibility for combining attributes to RenderableProvider
    private static final Attributes combineAttributes(final Renderable renderable) {
        tmpAttributes.clear();
        if (renderable.environment != null) tmpAttributes.set(renderable.environment);
        if (renderable.material != null) tmpAttributes.set(renderable.material);
        return tmpAttributes;
    }

    @Override
    public void begin(@NotNull Camera camera, @NotNull RenderContext context) {
        super.begin(camera, context);
        // Gdx.gl20.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
        // Gdx.gl20.glPolygonOffset(2.f, 100.f);
    }

    @Override
    public void end() {
        super.end();
        // Gdx.gl20.glDisable(GL20.GL_POLYGON_OFFSET_FILL);
    }

    @Override
    public boolean canRender(Renderable renderable) {
        if (renderable.bones != null) {
            if (renderable.bones.length > config.numBones) return false;
            if (renderable.meshPart.mesh.getVertexAttributes().getBoneWeights() > config.numBoneWeights) return false;
        }
        final Attributes attributes = combineAttributes(renderable);

        boolean isBlendedTextureShader = (attributesMask & BlendingAttribute.Type) == BlendingAttribute.Type
                && (attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse;

        boolean isBlendedTextureRenderable = attributes.has(BlendingAttribute.Type) && attributes.has(TextureAttribute.Diffuse);

        if (isBlendedTextureShader != isBlendedTextureRenderable) return false;

        return (renderable.bones != null) == (numBones > 0);
    }

    @Override
    public void render(Renderable renderable, Attributes combinedAttributes) {
        if (combinedAttributes.has(BlendingAttribute.Type)) {
            final BlendingAttribute blending = (BlendingAttribute) combinedAttributes.get(BlendingAttribute.Type);
            combinedAttributes.remove(BlendingAttribute.Type);
            final boolean hasAlphaTest = combinedAttributes.has(FloatAttribute.AlphaTest);
            if (!hasAlphaTest) combinedAttributes.set(alphaTestAttribute);
            if (blending.opacity >= ((FloatAttribute) combinedAttributes.get(FloatAttribute.AlphaTest)).value)
                super.render(renderable, combinedAttributes);
            if (!hasAlphaTest) combinedAttributes.remove(FloatAttribute.AlphaTest);
            combinedAttributes.set(blending);
        } else
            super.render(renderable, combinedAttributes);
    }

    public static class Config extends DefaultShader.Config {
        public boolean depthBufferOnly = false;
        public float defaultAlphaTest = 0.5f;

        public Config() {
            super();
            defaultCullFace = GLES20.GL_FRONT;
        }

        public Config(String vertexShader, String fragmentShader) {
            super(vertexShader, fragmentShader);
        }
    }
}
