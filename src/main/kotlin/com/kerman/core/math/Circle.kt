package com.kerman.core.math

import com.kerman.core.utils.NumberUtils
import java.io.Serializable

/**
 * Info : This class was inspired by "com.badlogic.gdx.math.Circle".
 *
 * A convenient 2D circle class.
 */
class Circle(
    @JvmField var x: Float = 0F,
    @JvmField var y: Float = 0F,
    @JvmField var radius: Float = 0F
) : Serializable, Shape2D {

    /**
     * Constructs a new circle using a given [Vector2] that contains the desired X and Y coordinates, and a given radius.
     *
     * @param position The position [Vector2] of the center of the circle.
     * @param radius   The radius.
     */
    constructor(position: Vector2, radius: Float) : this(position.x, position.y, radius)

    /**
     * Copy constructor
     *
     * @param circle The circle to construct a copy of.
     */
    constructor(circle: Circle) : this(circle.x, circle.y, circle.radius)

    /**
     * Creates a new [Circle] in terms of its center and a point on its edge.
     *
     * @param center The center of the new circle
     * @param edge   Any point on the edge of the given circle
     */
    constructor(center: Vector2, edge: Vector2) : this(
        center.x,
        center.y,
        Vector2.len(center.x - edge.x, center.y - edge.y)
    )

    /**
     * Sets a new location and radius for this circle.
     *
     * @param x      X coordinate of the center of the circle
     * @param y      Y coordinate of the center of the circle
     * @param radius Circle radius
     */
    fun set(x: Float, y: Float, radius: Float) {
        this.x = x
        this.y = y
        this.radius = radius
    }

    /**
     * Sets a new location and radius for this circle.
     *
     * @param position Position [Vector2] for this circle.
     * @param radius   Circle radius
     */
    fun set(position: Vector2, radius: Float) {
        this.x = position.x
        this.y = position.y
        this.radius = radius
    }

    /**
     * Sets a new location and radius for this circle, based upon another circle.
     *
     * @param circle The circle to copy the position and radius of.
     */
    fun set(circle: Circle) {
        this.x = circle.x
        this.y = circle.y
        this.radius = circle.radius
    }

    /**
     * Sets this [Circle]'s values in terms of its center and a point on its edge.
     *
     * @param center The new center of the circle
     * @param edge   Any point on the edge of the given circle
     */
    fun set(center: Vector2, edge: Vector2) {
        this.x = center.x
        this.y = center.y
        this.radius = Vector2.len(center.x - edge.x, center.y - edge.y)
    }

    /**
     * Sets the x and y-coordinates of circle center from vector
     *
     * @param position The position vector
     */
    fun setPosition(position: Vector2) {
        this.x = position.x
        this.y = position.y
    }

    /**
     * Sets the x and y-coordinates of circle center
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    /**
     * Sets the x-coordinate of circle center
     *
     * @param x The x-coordinate
     */
    fun setX(x: Float) {
        this.x = x
    }

    /**
     * Sets the y-coordinate of circle center
     *
     * @param y The y-coordinate
     */
    fun setY(y: Float) {
        this.y = y
    }

    /**
     * Sets the radius of circle
     *
     * @param radius The radius
     */
    fun setRadius(radius: Float) {
        this.radius = radius
    }

    /**
     * Checks whether or not this circle contains a given point.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if this circle contains the given point.
     */
    override fun contains(x: Float, y: Float): Boolean {
        val dx = this.x - x
        val dy = this.y - y
        return dx * dx + dy * dy <= radius * radius
    }

    /**
     * Checks whether or not this circle contains a given point.
     *
     * @param point The [Vector2] that contains the point coordinates.
     * @return true if this circle contains this point; false otherwise.
     */
    override fun contains(point: Vector2): Boolean {
        val dx = x - point.x
        val dy = y - point.y
        return dx * dx + dy * dy <= radius * radius
    }

    /**
     * @param c the other [Circle]
     * @return whether this circle contains the other circle.
     */
    fun contains(c: Circle): Boolean {
        val radiusDiff = radius - c.radius
        if (radiusDiff < 0F) return false // Can't contain bigger circle
        val dx = x - c.x
        val dy = y - c.y
        val dst = dx * dx + dy * dy
        val radiusSum = radius + c.radius
        return !(radiusDiff * radiusDiff < dst) && (dst < radiusSum * radiusSum)
    }

    /**
     * @param c the other [Circle]
     * @return whether this circle overlaps the other circle.
     */
    fun overlaps(c: Circle): Boolean {
        val dx = x - c.x
        val dy = y - c.y
        val distance = dx * dx + dy * dy
        val radiusSum = radius + c.radius
        return distance < radiusSum * radiusSum
    }

    /**
     * Returns a [String] representation of this [Circle] of the form `x,y,radius`.
     */
    override fun toString(): String {
        return "$x,$y,$radius"
    }

    /**
     * @return The circumference of this circle (as 2 * [MathUtils.PI2]) * `radius`
     */
    fun circumference(): Float {
        return this.radius * MathUtils.PI2
    }

    /**
     * @return The area of this circle (as [MathUtils.PI] * radius * radius).
     */
    fun area() = this.radius * this.radius * MathUtils.PI

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || other.javaClass != this.javaClass) return false
        val c = other as Circle
        return this.x == c.x && this.y == c.y && this.radius == c.radius
    }

    override fun hashCode(): Int {
        val prime = 41
        var result = 1
        result = prime * result + NumberUtils.floatToRawIntBits(radius)
        result = prime * result + NumberUtils.floatToRawIntBits(x)
        result = prime * result + NumberUtils.floatToRawIntBits(y)
        return result
    }

    /**
     * Operator function that allows to deconstruct this circle.
     * @return X component.
     */
    operator fun component1() = this.x

    /**
     * Operator function that allows to deconstruct this circle.
     * @return Y component.
     */
    operator fun component2() = this.y

    /**
     * Operator function that allows to deconstruct this circle.
     * @return Radius component.
     */
    operator fun component3() = this.radius
}