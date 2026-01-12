package com.kerman.core.scenes.scene2d.actions;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.DelayAction".
 * <p>
 * Delays execution of an action or inserts a pause in a {@link SequenceAction}.
 */
public class DelayAction extends DelegateAction implements FinishableAction {
    private float duration, time;

    public DelayAction() {
    }

    public DelayAction(float duration) {
        this.duration = duration;
    }

    protected boolean delegate(float delta) {
        if (time < duration) {
            time += delta;
            if (time < duration) return false;
            delta = time - duration;
        }
        if (action == null) return true;
        return action.act(delta);
    }

    /**
     * Causes the delay to be complete.
     */
    public void finish() {
        time = duration;
    }

    public void restart() {
        super.restart();
        time = 0;
    }

    /**
     * Gets the time spent waiting for the delay.
     */
    public float getTime() {
        return time;
    }

    /**
     * Sets the time spent waiting for the delay.
     */
    public void setTime(float time) {
        this.time = time;
    }

    public float getDuration() {
        return duration;
    }

    /**
     * Sets the length of the delay in seconds.
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }
}
