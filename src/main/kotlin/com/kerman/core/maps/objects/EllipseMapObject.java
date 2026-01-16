package com.kerman.core.maps.objects;

import com.kerman.core.maps.MapObject;
import com.kerman.core.math.Ellipse;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.objects.EllipseMapObject".
 * <p>
 * Represents {@link Ellipse} map objects.
 */
public class EllipseMapObject extends MapObject {

    private final Ellipse ellipse;

    /**
     * Creates an {@link Ellipse} object whose lower left corner is at (0, 0) with width=1 and height=1
     */
    public EllipseMapObject() {
        this(0.0f, 0.0f, 1.0f, 1.0f);
    }

    /**
     * Creates an {@link Ellipse} object with the given X and Y coordinates along with a specified width and height.
     *
     * @param x      X coordinate
     * @param y      Y coordinate
     * @param width  Width in pixels
     * @param height Height in pixels
     */
    public EllipseMapObject(float x, float y, float width, float height) {
        super();
        ellipse = new Ellipse(x, y, width, height);
    }

    /**
     * @return ellipse shape
     */
    public Ellipse getEllipse() {
        return ellipse;
    }
}
