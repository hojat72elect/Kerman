package com.kerman.core.graphics.k3d;

import com.kerman.core.graphics.k3d.attributes.DirectionalLightsAttribute;
import com.kerman.core.graphics.k3d.attributes.PointLightsAttribute;
import com.kerman.core.graphics.k3d.attributes.SpotLightsAttribute;
import com.kerman.core.graphics.k3d.environment.BaseLight;
import com.kerman.core.graphics.k3d.environment.DirectionalLight;
import com.kerman.core.graphics.k3d.environment.PointLight;
import com.kerman.core.graphics.k3d.environment.ShadowMap;
import com.kerman.core.graphics.k3d.environment.SpotLight;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.KermanRuntimeException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.Environment".
 */
public class Environment extends Attributes {

    /**
     * Shadow map used to render shadows
     */
    public ShadowMap shadowMap;

    public Environment() {
    }

    public Environment add(final BaseLight... lights) {
        for (final BaseLight light : lights)
            add(light);
        return this;
    }

    public Environment add(final KermanArray<BaseLight> lights) {
        for (final BaseLight light : lights)
            add(light);
        return this;
    }

    public Environment add(BaseLight light) {
        if (light instanceof DirectionalLight)
            add((DirectionalLight) light);
        else if (light instanceof PointLight) {
            add((PointLight) light);
        } else if (light instanceof SpotLight)
            add((SpotLight) light);
        else
            throw new KermanRuntimeException("Unknown light type");
        return this;
    }

    public Environment add(DirectionalLight light) {
        DirectionalLightsAttribute dirLights = ((DirectionalLightsAttribute) get(DirectionalLightsAttribute.Type));
        if (dirLights == null) set(dirLights = new DirectionalLightsAttribute());
        dirLights.lights.add(light);
        return this;
    }

    public Environment add(PointLight light) {
        PointLightsAttribute pointLights = ((PointLightsAttribute) get(PointLightsAttribute.Type));
        if (pointLights == null) set(pointLights = new PointLightsAttribute());
        pointLights.lights.add(light);
        return this;
    }

    public Environment add(SpotLight light) {
        SpotLightsAttribute spotLights = ((SpotLightsAttribute) get(SpotLightsAttribute.Type));
        if (spotLights == null) set(spotLights = new SpotLightsAttribute());
        spotLights.lights.add(light);
        return this;
    }

    public Environment remove(final BaseLight... lights) {
        for (final BaseLight light : lights)
            remove(light);
        return this;
    }

    public Environment remove(final KermanArray<BaseLight> lights) {
        for (final BaseLight light : lights)
            remove(light);
        return this;
    }

    public Environment remove(BaseLight light) {
        if (light instanceof DirectionalLight)
            remove((DirectionalLight) light);
        else if (light instanceof PointLight)
            remove((PointLight) light);
        else if (light instanceof SpotLight)
            remove((SpotLight) light);
        else
            throw new KermanRuntimeException("Unknown light type");
        return this;
    }

    public Environment remove(DirectionalLight light) {
        if (has(DirectionalLightsAttribute.Type)) {
            DirectionalLightsAttribute dirLights = ((DirectionalLightsAttribute) get(DirectionalLightsAttribute.Type));
            dirLights.lights.removeValue(light, false);
            if (dirLights.lights.size == 0) remove(DirectionalLightsAttribute.Type);
        }
        return this;
    }

    public Environment remove(PointLight light) {
        if (has(PointLightsAttribute.Type)) {
            PointLightsAttribute pointLights = ((PointLightsAttribute) get(PointLightsAttribute.Type));
            pointLights.lights.removeValue(light, false);
            if (pointLights.lights.size == 0) remove(PointLightsAttribute.Type);
        }
        return this;
    }

    public Environment remove(SpotLight light) {
        if (has(SpotLightsAttribute.Type)) {
            SpotLightsAttribute spotLights = ((SpotLightsAttribute) get(SpotLightsAttribute.Type));
            spotLights.lights.removeValue(light, false);
            if (spotLights.lights.size == 0) remove(SpotLightsAttribute.Type);
        }
        return this;
    }
}
