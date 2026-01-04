package com.kerman.core.graphics.k3d.model.data;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe".
 */
public class ModelNodeKeyframe<T> {
    /**
     * the timestamp of the keyframe in seconds.
     **/
    public float keytime;
    /**
     * the value of the keyframe.
     */
    public T value = null;
}
