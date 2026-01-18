package com.kerman.core.graphics.k3d.environment;

import com.kerman.core.graphics.Color;

/**
 * Info : This abstract class was inspired by "com.badlogic.gdx.graphics.g3d.environment.BaseLight".
 */
public abstract class BaseLight<T extends BaseLight<T>> {
    public final Color color = new Color(0, 0, 0, 1);

    public T setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        return (T) this;
    }

    public T setColor(Color color) {
        this.color.set(color);
        return (T) this;
    }
}
