package com.kerman.core.graphics.k3d.model;

import com.kerman.core.graphics.k3d.Model;
import com.kerman.core.math.Quaternion;
import com.kerman.core.math.Vector3;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.NodeAnimation".
 * <p>
 * A NodeAnimation defines keyframes for a {@link Node} in a {@link Model}. The keyframes are given as a translation vector, a
 * rotation quaternion and a scale vector. Keyframes are interpolated linearly for now. Keytimes are given in seconds.
 */
public class NodeAnimation {
    /**
     * the Node affected by this animation
     **/
    public Node node;
    /**
     * the translation keyframes if any (might be null), sorted by time ascending
     **/
    public KermanArray<NodeKeyframe<Vector3>> translation = null;
    /**
     * the rotation keyframes if any (might be null), sorted by time ascending
     **/
    public KermanArray<NodeKeyframe<Quaternion>> rotation = null;
    /**
     * the scaling keyframes if any (might be null), sorted by time ascending
     **/
    public KermanArray<NodeKeyframe<Vector3>> scaling = null;
}
