package com.kerman.core.graphics.k3d.model;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.NodeKeyframe".
 * <p>
 * A NodeKeyframe specifies the value (e.g. the translation, rotation or scale) of a frame within a {@link NodeAnimation}.
 */
public class NodeKeyframe<T> {
    /**
     * the value of this keyframe at the specified timestamp
     **/
    public final T value;
    /**
     * the timestamp of this keyframe
     **/
    public float keytime;

    public NodeKeyframe(final float t, final T v) {
        keytime = t;
        value = v;
    }
}
