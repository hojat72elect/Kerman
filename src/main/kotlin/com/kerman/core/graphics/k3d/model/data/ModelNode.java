package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.math.Quaternion;
import com.kerman.core.math.Vector3;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelNode".
 */
public class ModelNode {
    public String id;
    public Vector3 translation;
    public Quaternion rotation;
    public Vector3 scale;
    public String meshId;
    public ModelNodePart[] parts;
    public ModelNode[] children;
}
