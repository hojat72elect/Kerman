package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.graphics.Color;
import com.kerman.core.utils.KermanArray;

/**
 * This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial".
 */
public class ModelMaterial {
    public String id;
    public MaterialType type;
    public Color ambient;
    public Color diffuse;
    public Color specular;
    public Color emissive;
    public Color reflection;
    public float shininess;
    public float opacity = 1.f;
    public KermanArray<ModelTexture> textures;

    public enum MaterialType {
        Lambert, Phong
    }
}
