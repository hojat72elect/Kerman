package com.kerman.core.graphics.k3d.attributes;

import com.kerman.core.graphics.Cubemap;
import com.kerman.core.graphics.k3d.Attribute;
import com.kerman.core.graphics.k3d.utils.TextureDescriptor;
import com.kerman.core.utils.KermanRuntimeException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute".
 */
public class CubemapAttribute extends Attribute {
    public final static String EnvironmentMapAlias = "environmentCubemap";
    public final static long EnvironmentMap = register(EnvironmentMapAlias);

    protected static long Mask = EnvironmentMap;
    public final TextureDescriptor<Cubemap> textureDescription;

    public CubemapAttribute(final long type) {
        super(type);
        if (!is(type)) throw new KermanRuntimeException("Invalid type specified");
        textureDescription = new TextureDescriptor<>();
    }

    public <T extends Cubemap> CubemapAttribute(final long type, final TextureDescriptor<T> textureDescription) {
        this(type);
        this.textureDescription.set(textureDescription);
    }

    public CubemapAttribute(final long type, final Cubemap texture) {
        this(type);
        textureDescription.texture = texture;
    }

    public CubemapAttribute(final CubemapAttribute copyFrom) {
        this(copyFrom.type, copyFrom.textureDescription);
    }

    public final static boolean is(final long mask) {
        return (mask & Mask) != 0;
    }

    @Override
    public Attribute copy() {
        return new CubemapAttribute(this);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 967 * result + textureDescription.hashCode();
        return result;
    }

    @Override
    public int compareTo(Attribute o) {
        if (type != o.type) return (int) (type - o.type);
        return textureDescription.compareTo(((CubemapAttribute) o).textureDescription);
    }
}
