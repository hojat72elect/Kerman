package com.kerman.core.graphics.k3d.model.data;

import com.kerman.core.assets.loaders.ModelLoader;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.KermanRuntimeException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.model.data.ModelData".
 * <p>
 * Returned by a {@link ModelLoader}, contains meshes, materials, nodes and animations. OpenGL resources like textures or vertex
 * buffer objects are not stored. Instead, a ModelData instance needs to be converted to a Model first.
 */
public class ModelData {
    public final short[] version = new short[2];
    public final KermanArray<ModelMesh> meshes = new KermanArray<>();
    public final KermanArray<ModelMaterial> materials = new KermanArray<>();
    public final KermanArray<ModelNode> nodes = new KermanArray<>();
    public final KermanArray<ModelAnimation> animations = new KermanArray<>();
    public String id;

    public void addMesh(ModelMesh mesh) {
        for (ModelMesh other : meshes) {
            if (other.id.equals(mesh.id)) {
                throw new KermanRuntimeException("Mesh with id '" + other.id + "' already in model");
            }
        }
        meshes.add(mesh);
    }
}
