package com.kerman.core.graphics.k3d.particles.values;

import com.kerman.core.math.MathUtils;
import com.kerman.core.utils.Json;
import com.kerman.core.utils.JsonValue;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.particles.values.RangedNumericValue".
 * <p>
 * A value which has a defined minimum and maximum bounds.
 */
public class RangedNumericValue extends ParticleValue {
    private float lowMin, lowMax;

    public float newLowValue() {
        return lowMin + (lowMax - lowMin) * MathUtils.random();
    }

    public void setLow(float value) {
        lowMin = value;
        lowMax = value;
    }

    public void setLow(float min, float max) {
        lowMin = min;
        lowMax = max;
    }

    public float getLowMin() {
        return lowMin;
    }

    public void setLowMin(float lowMin) {
        this.lowMin = lowMin;
    }

    public float getLowMax() {
        return lowMax;
    }

    public void setLowMax(float lowMax) {
        this.lowMax = lowMax;
    }

    public void load(RangedNumericValue value) {
        super.load(value);
        lowMax = value.lowMax;
        lowMin = value.lowMin;
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("lowMin", lowMin);
        json.writeValue("lowMax", lowMax);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        lowMin = json.readValue("lowMin", float.class, jsonData);
        lowMax = json.readValue("lowMax", float.class, jsonData);
    }
}
