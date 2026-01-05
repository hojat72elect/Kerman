package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.math.Quaternion;
import com.kerman.core.math.Vector3;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation".
 */
public class ModelNodeAnimation {
    /**
     * the id of the node animated by this animation.
     **/
    public String nodeId;
    /**
     * the keyframes, defining the translation of a node for a specific timestamp
     **/
    public KermanArray<ModelNodeKeyframe<Vector3>> translation;
    /**
     * the keyframes, defining the rotation of a node for a specific timestamp
     **/
    public KermanArray<ModelNodeKeyframe<Quaternion>> rotation;
    /**
     * the keyframes, defining the scaling of a node for a specific timestamp
     **/
    public KermanArray<ModelNodeKeyframe<Vector3>> scaling;
}
