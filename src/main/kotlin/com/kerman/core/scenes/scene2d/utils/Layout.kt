package com.kerman.core.scenes.scene2d.utils

import com.kerman.core.scenes.scene2d.Actor

/**
 * Info : This interface was inspired by "com.badlogic.gdx.scenes.scene2d.utils.Layout".
 *
 * Provides functions for an actor to participate in layout and to provide a minimum, preferred, and maximum size.
 */
interface Layout {
    /**
     * Computes and caches any information needed for drawing and, if this actor has children, positions and sizes each child,
     * calls [invalidate] on any each child whose width or height has changed, and calls [validate] on each
     * child. This method should almost never be called directly, instead [validate] should be used.
     */
    fun layout()

    /**
     * Invalidates this actor's layout, causing [layout] to happen the next time [validate] is called. This
     * method should be called when state changes in the actor that requires a layout but does not change the minimum, preferred,
     * maximum, or actual size of the actor (meaning it does not affect the parent actor's layout).
     */
    fun invalidate()

    /**
     * Invalidates this actor and its ascendants, calling [invalidate] on each. This method should be called when state
     * changes in the actor that affects the minimum, preferred, maximum, or actual size of the actor (meaning it potentially
     * affects the parent actor's layout).
     */
    fun invalidateHierarchy()

    /**
     * Ensures the actor has been laid out. Calls [layout] if [invalidate] has been called since the last time
     * [validate] was called, or if the actor otherwise needs to be laid out. This method is usually called in
     * [Actor.draw] by the actor itself before drawing is performed.
     */
    fun validate()

    /**
     * Sizes this actor to its preferred width and height, then calls [validate].
     * Generally this method should not be called in an actor's constructor because it calls [layout], which means a
     * subclass would have layout() called before the subclass' constructor. Instead, in constructors simply set the actor's size
     * to [getPrefWidth] and [getPrefHeight]. This allows the actor to have a size at construction time for more
     * convenient use with groups that do not layout their children.
     */
    fun pack()

    /**
     * If true, this actor will be sized to the parent in [validate]. If the parent is the stage, the actor will be sized
     * to the stage. This method is for convenience only when the widget's parent does not set the size of its children (such as
     * the stage).
     */
    fun setFillParent(fillParent: Boolean)

    /**
     * Enables or disables the layout for this actor and all child actors, recursively. When false, [validate] will not
     * cause a layout to occur. This can be useful when an actor will be manipulated externally, such as with actions. Default is
     * true.
     */
    fun setLayoutEnabled(enabled: Boolean)
    fun getMinWidth(): Float
    fun getMinHeight(): Float
    fun getPrefWidth(): Float
    fun getPrefHeight(): Float

    /**
     * Zero indicates no max width.
     */
    fun getMaxWidth(): Float

    /**
     * Zero indicates no max height.
     */
    fun getMaxHeight(): Float
}
