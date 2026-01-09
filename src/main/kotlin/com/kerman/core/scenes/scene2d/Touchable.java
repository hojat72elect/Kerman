package com.kerman.core.scenes.scene2d;

/**
 * Info : This enum was inspired by "com.badlogic.gdx.scenes.scene2d.Touchable".
 * <p>
 * Determines how touch input events are distributed to an actor and any children.
 */
public enum Touchable {
    /**
     * All touch input events will be received by the actor and any children.
     */
    enabled,
    /**
     * No touch input events will be received by the actor or any children.
     */
    disabled,
    /**
     * No touch input events will be received by the actor, but children will still receive events. Note that events on the
     * children will still bubble to the parent.
     */
    childrenOnly
}
