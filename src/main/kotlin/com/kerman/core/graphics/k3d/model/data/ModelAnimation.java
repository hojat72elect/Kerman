package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation".
 */
public class ModelAnimation {
    public String id;
    public KermanArray<ModelNodeAnimation> nodeAnimations = new KermanArray<>();
}
