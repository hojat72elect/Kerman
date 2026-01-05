package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.math.Matrix4;
import com.kerman.core.utils.ArrayMap;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart".
 */
public class ModelNodePart {
    public String materialId;
    public String meshPartId;
    public ArrayMap<String, Matrix4> bones;
    public int[][] uvMapping;
}
