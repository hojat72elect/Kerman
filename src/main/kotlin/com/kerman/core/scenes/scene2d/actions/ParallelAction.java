package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;
import com.kerman.core.scenes.scene2d.Actor;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.Pool;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.ParallelAction".
 * <p>
 * Executes a number of actions at the same time.
 */
public class ParallelAction extends Action {
    KermanArray<Action> actions = new KermanArray<>(4);
    private boolean complete;

    public ParallelAction() {
    }

    public ParallelAction(Action action1) {
        addAction(action1);
    }

    public ParallelAction(Action action1, Action action2) {
        addAction(action1);
        addAction(action2);
    }

    public ParallelAction(Action action1, Action action2, Action action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4, Action action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act(float delta) {
        if (complete) return true;
        complete = true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executing.
        try {
            KermanArray<Action> actions = this.actions;
            for (int i = 0, n = actions.size; i < n && actor != null; i++) {
                Action currentAction = actions.get(i);
                if (currentAction.getActor() != null && !currentAction.act(delta)) complete = false;
                if (actor == null) return true; // This action was removed.
            }
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void restart() {
        complete = false;
        KermanArray<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).restart();
    }

    public void reset() {
        super.reset();
        actions.clear();
    }

    public void addAction(Action action) {
        actions.add(action);
        if (actor != null) action.setActor(actor);
    }

    public void setActor(Actor actor) {
        KermanArray<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).setActor(actor);
        super.setActor(actor);
    }

    public KermanArray<Action> getActions() {
        return actions;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append('(');
        KermanArray<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++) {
            if (i > 0) buffer.append(", ");
            buffer.append(actions.get(i));
        }
        buffer.append(')');
        return buffer.toString();
    }
}
