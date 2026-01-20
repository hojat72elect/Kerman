package com.kerman.backends.lwjgl3;

import org.jetbrains.annotations.NotNull;

/**
 * Convenience implementation of {@link Lwjgl3WindowListener}. Derive from this class and only overwrite the methods you are
 * interested in.
 */
public class Lwjgl3WindowAdapter implements Lwjgl3WindowListener {
    @Override
    public void created(@NotNull Lwjgl3Window window) {
    }

    @Override
    public void iconified(boolean isIconified) {
    }

    @Override
    public void maximized(boolean isMaximized) {
    }

    @Override
    public void focusLost() {
    }

    @Override
    public void focusGained() {
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public void filesDropped(@NotNull String[] files) {
    }

    @Override
    public void refreshRequested() {
    }
}
