package com.kerman.core.graphics.k3d.utils;

import com.kerman.core.graphics.Camera;
import com.kerman.core.graphics.k3d.Renderable;
import com.kerman.core.graphics.k3d.attributes.BlendingAttribute;
import com.kerman.core.math.Matrix4;
import com.kerman.core.math.Vector3;
import com.kerman.core.utils.KermanArray;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.utils.DefaultRenderableSorter".
 */
public class DefaultRenderableSorter implements RenderableSorter, Comparator<Renderable> {
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();
    private Camera camera;

    @Override
    public void sort(@NotNull final Camera camera, final KermanArray<Renderable> renderables) {
        this.camera = camera;
        renderables.sort(this);
    }

    private Vector3 getTranslation(Matrix4 worldTransform, Vector3 center, Vector3 output) {
        if (center.isZero())
            worldTransform.getTranslation(output);
        else if (!worldTransform.hasRotationOrScaling())
            worldTransform.getTranslation(output).add(center);
        else
            output.set(center).mul(worldTransform);
        return output;
    }

    @Override
    public int compare(final Renderable o1, final Renderable o2) {
        final boolean b1 = o1.material.has(BlendingAttribute.Type)
                && ((BlendingAttribute) o1.material.get(BlendingAttribute.Type)).blended;
        final boolean b2 = o2.material.has(BlendingAttribute.Type)
                && ((BlendingAttribute) o2.material.get(BlendingAttribute.Type)).blended;
        if (b1 != b2) return b1 ? 1 : -1;
        // FIXME implement better sorting algorithm
        getTranslation(o1.worldTransform, o1.meshPart.center, tmpV1);
        getTranslation(o2.worldTransform, o2.meshPart.center, tmpV2);
        final float dst = (int) (1000f * camera.position.dst2(tmpV1)) - (int) (1000f * camera.position.dst2(tmpV2));
        final int result = dst < 0 ? -1 : (dst > 0 ? 1 : 0);
        return b1 ? -result : result;
    }
}
