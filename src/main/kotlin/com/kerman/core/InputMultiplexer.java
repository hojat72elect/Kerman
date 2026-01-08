package com.kerman.core;

import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.SnapshotArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.InputMultiplexer".
 * <p>
 * An {@link InputProcessor} that delegates to an ordered list of other InputProcessors. Delegation for an event stops if a
 * processor returns true, which indicates that the event was handled.
 */
public class InputMultiplexer implements InputProcessor {
    private final SnapshotArray<InputProcessor> processors = new SnapshotArray<>(4);

    public InputMultiplexer() {
    }

    public InputMultiplexer(InputProcessor... processors) {
        this.processors.addAll(processors);
    }

    public void addProcessor(int index, InputProcessor processor) {
        if (processor == null) throw new NullPointerException("processor cannot be null");
        processors.insert(index, processor);
    }

    /**
     * Removes and returns the item at the specified index.
     */
    public InputProcessor removeProcessor(int index) {
        return processors.removeIndex(index);
    }

    public void addProcessor(InputProcessor processor) {
        if (processor == null) throw new NullPointerException("processor cannot be null");
        processors.add(processor);
    }

    /**
     * Removes the given processor, using identity comparison (==)
     *
     * @return true if the processor was found and removed, false otherwise
     */
    public boolean removeProcessor(InputProcessor processor) {
        return processors.removeValue(processor, true);
    }

    /**
     * @return the number of processors in this multiplexer
     */
    public int size() {
        return processors.size;
    }

    public void clear() {
        processors.clear();
    }

    public SnapshotArray<InputProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(InputProcessor... processors) {
        this.processors.clear();
        this.processors.addAll(processors);
    }

    public void setProcessors(KermanArray<InputProcessor> processors) {
        this.processors.clear();
        this.processors.addAll(processors);
    }

    public boolean keyDown(int keycode) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).keyDown(keycode)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).keyUp(keycode)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean keyTyped(char character) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).keyTyped(character)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).touchDown(screenX, screenY, pointer, button)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).touchUp(screenX, screenY, pointer, button)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).touchCancelled(screenX, screenY, pointer, button)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).touchDragged(screenX, screenY, pointer)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).mouseMoved(screenX, screenY)) return true;
        } finally {
            processors.end();
        }
        return false;
    }

    public boolean scrolled(float amountX, float amountY) {
        Object[] items = processors.begin();
        try {
            for (int i = 0, n = processors.size; i < n; i++)
                if (((InputProcessor) items[i]).scrolled(amountX, amountY)) return true;
        } finally {
            processors.end();
        }
        return false;
    }
}
