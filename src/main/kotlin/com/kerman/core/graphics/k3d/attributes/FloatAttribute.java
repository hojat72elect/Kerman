package com.kerman.core.graphics.k3d.attributes;

import com.kerman.core.graphics.k3d.Attribute;
import com.kerman.core.math.MathUtils;
import com.kerman.core.utils.NumberUtils;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute".
 */
public class FloatAttribute extends Attribute {
    public static final String ShininessAlias = "shininess";
    public static final long Shininess = register(ShininessAlias);
    public static final String AlphaTestAlias = "alphaTest";
    public static final long AlphaTest = register(AlphaTestAlias);
    public float value;

    public FloatAttribute(long type) {
        super(type);
    }

    public FloatAttribute(long type, float value) {
        super(type);
        this.value = value;
    }

    public static FloatAttribute createShininess(float value) {
        return new FloatAttribute(Shininess, value);
    }

    public static FloatAttribute createAlphaTest(float value) {
        return new FloatAttribute(AlphaTest, value);
    }

    @Override
    public Attribute copy() {
        return new FloatAttribute(type, value);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 977 * result + NumberUtils.floatToRawIntBits(value);
        return result;
    }

    @Override
    public int compareTo(Attribute o) {
        if (type != o.type) return (int) (type - o.type);
        final float v = ((FloatAttribute) o).value;
        return MathUtils.isEqual(value, v) ? 0 : value < v ? -1 : 1;
    }
}
