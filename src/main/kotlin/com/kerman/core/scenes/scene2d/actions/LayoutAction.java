package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;
import com.kerman.core.scenes.scene2d.Actor;
import com.kerman.core.scenes.scene2d.utils.Layout;
import com.kerman.core.utils.KermanRuntimeException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.LayoutAction".
 * <p>
 * Sets an actor's {@link Layout#setLayoutEnabled(boolean) layout} to enabled or disabled. The actor must implements
 * {@link Layout}.
 */
public class LayoutAction extends Action {
    private boolean enabled;

    public void setTarget(Actor actor) {
        if (actor != null && !(actor instanceof Layout)) throw new KermanRuntimeException("Actor must implement layout: " + actor);
        super.setTarget(actor);
    }

    public boolean act(float delta) {
        ((Layout) target).setLayoutEnabled(enabled);
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setLayoutEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
