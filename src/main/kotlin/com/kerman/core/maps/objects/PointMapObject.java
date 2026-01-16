package com.kerman.core.maps.objects;

import com.kerman.core.maps.MapObject;
import com.kerman.core.math.Vector2;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.objects.PointMapObject".
 * <p>
 * Represents 2D points on the map
 */
public class PointMapObject extends MapObject {

    private final Vector2 point;

    /**
     * creates a 2D point map object at (0, 0)
     */
    public PointMapObject() {
        this(0, 0);
    }

    /**
     * Creates a 2D point map object
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public PointMapObject(float x, float y) {
        point = new Vector2(x, y);
    }

    /**
     * @return 2D point on the map as {@link Vector2}
     */
    public Vector2 getPoint() {
        return point;
    }
}
