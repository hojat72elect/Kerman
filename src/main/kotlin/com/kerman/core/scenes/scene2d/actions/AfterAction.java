package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;
import com.kerman.core.scenes.scene2d.Actor;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.AfterAction".
 * <p>
 * Executes an action only after all other actions on the actor at the time this action's target was set have finished.
 */
public class AfterAction extends DelegateAction {
    private final KermanArray<Action> waitForActions = new KermanArray<>(false, 4);

    public void setTarget(Actor target) {
        if (target != null) waitForActions.addAll(target.getActions());
        super.setTarget(target);
    }

    public void restart() {
        super.restart();
        waitForActions.clear();
    }

    protected boolean delegate(float delta) {
        KermanArray<Action> currentActions = target.getActions();
        if (currentActions.size == 1) waitForActions.clear();
        for (int i = waitForActions.size - 1; i >= 0; i--) {
            Action action = waitForActions.get(i);
            int index = currentActions.indexOf(action, true);
            if (index == -1) waitForActions.removeIndex(i);
        }
        if (waitForActions.size > 0) return false;
        return action.act(delta);
    }
}
