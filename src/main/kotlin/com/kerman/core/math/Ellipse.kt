package com.kerman.core.math

import com.kerman.core.utils.NumberUtils
import java.io.Serializable
import kotlin.math.sqrt

/**
 * Info : This class was inspired by "com.badlogic.gdx.math.Ellipse".
 *
 * A convenient 2D ellipse class, based on the circle class.
 * بیضی
 */
class Ellipse : Serializable, Shape2D {

    @JvmField
    var x = 0F

    @JvmField
    var y = 0F

    @JvmField
    var width = 0F

    @JvmField
    var height = 0F

    /**
     * Construct a new ellipse with all values set to zero.
     */
    constructor()

    /**
     * Copy constructor (copies characteristics of an ellipse into another ellipse).
     * @param ellipse Ellipse to construct a copy of.
     */
    constructor(ellipse: Ellipse) {
        this.x = ellipse.x
        this.y = ellipse.y
        this.width = ellipse.width
        this.height = ellipse.height
    }

    /**
     * @param x      X coordinate of the center of the ellipse.
     * @param y      Y coordinate of the center of the ellipse.
     * @param width  the width of the ellipse.
     * @param height the height of the ellipse.
     */
    constructor(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    /**
     * @param position Position vector of the center of the ellipse.
     * @param width    the width of the ellipse.
     * @param height   the height of the ellipse.
     */
    constructor(position: Vector2, width: Float, height: Float) {
        this.x = position.x
        this.y = position.y
        this.width = width
        this.height = height
    }

    /**
     * @param position Position vector of the center of the ellipse.
     * @param size Size vector.
     */
    constructor(position: Vector2, size: Vector2) {
        this.x = position.x
        this.y = position.y
        this.width = size.x
        this.height = size.y
    }

    /**
     * Constructs a new [Ellipse] from the position and radius of a [Circle] (since circles are special cases of ellipses).
     * @param circle The circle to take the values of.
     */
    constructor(circle: Circle) {
        this.x = circle.x
        this.y = circle.y
        this.width = circle.radius * 2F
        this.height = circle.radius * 2F
    }

    /**
     * Checks whether this ellipse contains the given point.
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if this ellipse contains the given point; false otherwise.
     */
    override fun contains(x: Float, y: Float): Boolean {
        val dx = x - this.x
        val dy = y - this.y

        return (dx * dx) / (width * 0.5F * width * 0.5F) + (dy * dy) / (height * 0.5F * height * 0.5F) <= 1.0F
    }

    /**
     * Checks whether this ellipse contains the given point.
     * @param point Position vector.
     * @return true if this ellipse contains the given point; false otherwise.
     */
    override fun contains(point: Vector2) = contains(point.x, point.y)

    /**
     * Sets a new position and size for this ellipse.
     * @param x      X coordinate of the center of the ellipse.
     * @param y      Y coordinate of the center of the ellipse.
     * @param width  the width of the ellipse.
     * @param height the height of the ellipse.
     */
    fun set(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    /**
     * Sets a new position and size for this ellipse based upon another ellipse.
     * @param ellipse The ellipse to copy the position and size of.
     */
    fun set(ellipse: Ellipse) {
        x = ellipse.x
        y = ellipse.y
        width = ellipse.width
        height = ellipse.height
    }

    /**
     * If I'm not mistaken, this function converts our ellipse to a circle.
     */
    fun set(circle: Circle) {
        this.x = circle.x
        this.y = circle.y
        this.width = circle.radius * 2F
        this.height = circle.radius * 2F
    }

    fun set(position: Vector2, size: Vector2) {
        this.x = position.x
        this.y = position.y
        this.width = size.x
        this.height = size.y
    }

    /**
     * Sets the x and y-coordinates of ellipse center from a [Vector2].
     * @param position The position vector of the center of the ellipse
     * @return this ellipse for chaining
     */
    fun setPosition(position: Vector2): Ellipse {
        this.x = position.x
        this.y = position.y

        return this
    }

    /**
     * Sets the x and y-coordinates of ellipse center
     * @param x The x-coordinate of the center of the ellipse
     * @param y The y-coordinate of the center of the ellipse
     * @return this ellipse for chaining
     */
    fun setPosition(x: Float, y: Float): Ellipse {
        this.x = x
        this.y = y

        return this
    }

    /**
     * Sets the width and height of this ellipse.
     * @param width  The width.
     * @param height The height.
     * @return this ellipse for chaining.
     */
    fun setSize(width: Float, height: Float): Ellipse {
        this.width = width
        this.height = height

        return this
    }

    /**
     * @return The area of this [Ellipse] as [MathUtils.PI] * [Ellipse.width] * [Ellipse.height].
     */
    fun area() = MathUtils.PI * (this.width * this.height) / 4

    /**
     * Approximates the circumference of this [Ellipse]. Oddly enough, the circumference of an ellipse is actually difficult to compute exactly.
     * @return The Ramanujan approximation to the circumference of an ellipse if one dimension is at least three times longer than the other, else the simpler
     * approximation.
     */
    fun circumference(): Float {
        val a = this.width / 2F
        val b = this.height / 2F
        return if (a * 3 > b || b * 3 > a) {
            // If one dimension is three times as long as the other...
            // This is Ramanujan's first approximation.
            (MathUtils.PI * ((3 * (a + b)) - sqrt((3 * a + b) * (a + 3 * b))))
        } else {
            // We can use the simpler approximation, then
            // This is Euler's approximation.
            (MathUtils.PI2 * sqrt((a * a + b * b) / 2))
        }
    }

    /**
     * Returns a [String] representation of this [Ellipse] of the form `[x,y,width,height]`.
     */
    override fun toString() = "[$x,$y,$width,$height]"

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || other.javaClass != this.javaClass) return false
        val e = other as Ellipse
        return this.x == e.x && this.y == e.y && this.width == e.width && this.height == e.height
    }

    override fun hashCode(): Int {
        val prime = 53
        var result = 1
        result = prime * result + NumberUtils.floatToRawIntBits(this.height)
        result = prime * result + NumberUtils.floatToRawIntBits(this.width)
        result = prime * result + NumberUtils.floatToRawIntBits(this.x)
        result = prime * result + NumberUtils.floatToRawIntBits(this.y)
        return result
    }

    /**
     * Operator function that allows to deconstruct this ellipse.
     * @return X component.
     */
    operator fun component1() = this.x

    /**
     * Operator function that allows to deconstruct this ellipse.
     * @return Y component.
     */
    operator fun component2() = this.y

    /**
     * Operator function that allows to deconstruct this ellipse.
     * @return Width component.
     */
    operator fun component3() = this.width

    /**
     * Operator function that allows to deconstruct this ellipse.
     * @return Height component.
     */
    operator fun component4() = this.height

    companion object {
        private const val serialVersionUID = 7381533206532032099L
    }
}
