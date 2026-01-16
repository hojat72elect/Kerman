package com.kerman.core.maps.objects;

import com.kerman.core.maps.MapObject;
import com.kerman.core.math.Polyline;

/** Info : This class was inspired by "com.badlogic.gdx.maps.objects.PolylineMapObject".
 *
 *
 * Represents {@link Polyline} map objects.
 */
public class PolylineMapObject extends MapObject {

    private Polyline polyline;

    /**
     * Creates empty polyline
     */
    public PolylineMapObject() {
        this(new float[0]);
    }

    /**
     * @param vertices polyline defining vertices
     */
    public PolylineMapObject(float[] vertices) {
        polyline = new Polyline(vertices);
    }

    /**
     * @param polyline the polyline
     */
    public PolylineMapObject(Polyline polyline) {
        this.polyline = polyline;
    }

    /**
     * @return polyline shape
     */
    public Polyline getPolyline() {
        return polyline;
    }

    /**
     * @param polyline new object's polyline shape
     */
    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }
}
