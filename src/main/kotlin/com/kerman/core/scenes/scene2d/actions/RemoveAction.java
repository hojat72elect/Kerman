package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.RemoveAction".
 * <p>
 * Removes an action from an actor.
 */
public class RemoveAction extends Action {
    private Action action;

    public boolean act(float delta) {
        target.removeAction(action);
        return true;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void reset() {
        super.reset();
        action = null;
    }
}
