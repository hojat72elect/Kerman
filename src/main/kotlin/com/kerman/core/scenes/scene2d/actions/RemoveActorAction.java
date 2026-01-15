package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction".
 * <p>
 * Removes an actor from the stage.
 */
public class RemoveActorAction extends Action {
    private boolean removed;

    public boolean act(float delta) {
        if (!removed) {
            removed = true;
            target.remove();
        }
        return true;
    }

    public void restart() {
        removed = false;
    }
}
