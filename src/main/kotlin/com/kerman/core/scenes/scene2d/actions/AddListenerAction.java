package com.kerman.core.scenes.scene2d.actions;

import com.kerman.core.scenes.scene2d.Action;
import com.kerman.core.scenes.scene2d.EventListener;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.actions.AddListenerAction".
 * <p>
 * Adds a listener to an actor.
 */
public class AddListenerAction extends Action {
    private EventListener listener;
    private boolean capture;

    public boolean act(float delta) {
        if (capture)
            target.addCaptureListener(listener);
        else
            target.addListener(listener);
        return true;
    }

    public EventListener getListener() {
        return listener;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public boolean getCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public void reset() {
        super.reset();
        listener = null;
    }
}
