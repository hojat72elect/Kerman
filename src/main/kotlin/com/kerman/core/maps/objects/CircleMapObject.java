package com.kerman.core.maps.objects;

import com.kerman.core.maps.MapObject;
import com.kerman.core.math.Circle;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.objects.CircleMapObject".
 * <p>
 * Represents {@link Circle} shaped map objects.
 */
public class CircleMapObject extends MapObject {

    private final Circle circle;

    /**
     * Creates a circle map object at (0,0) with r=1.0
     */
    public CircleMapObject() {
        this(0.0f, 0.0f, 1.0f);
    }

    /**
     * Creates a circle map object
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param radius Radius of the circle object.
     */
    public CircleMapObject(float x, float y, float radius) {
        super();
        circle = new Circle(x, y, radius);
    }

    /**
     * @return circle shape
     */
    public Circle getCircle() {
        return circle;
    }
}
