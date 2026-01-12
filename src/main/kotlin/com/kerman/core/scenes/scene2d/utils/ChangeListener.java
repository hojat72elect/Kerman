package com.kerman.core.scenes.scene2d.utils;

import com.kerman.core.scenes.scene2d.Actor;
import com.kerman.core.scenes.scene2d.Event;
import com.kerman.core.scenes.scene2d.EventListener;

import org.jetbrains.annotations.NotNull;

/**
 * Info : This abstract class was inspired by "com.badlogic.gdx.scenes.scene2d.utils.ChangeListener".
 * <p>
 * Listener for {@link ChangeEvent}.
 */
abstract public class ChangeListener implements EventListener {
    public boolean handle(@NotNull Event event) {
        if (!(event instanceof ChangeEvent)) return false;
        changed((ChangeEvent) event, event.getTarget());
        return false;
    }

    /**
     * @param actor The event target, which is the actor that emitted the change event.
     */
    abstract public void changed(ChangeEvent event, Actor actor);

    /**
     * Fired when something in an actor has changed. This is a generic event, exactly what changed in an actor will vary.
     */
    static public class ChangeEvent extends Event {
    }
}
