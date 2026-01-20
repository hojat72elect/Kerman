package com.kerman.backends.lwjgl3;

import com.kerman.core.Kerman;
import com.kerman.core.utils.Clipboard;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

/**
 * Clipboard implementation for desktop that uses the system clipboard via GLFW.
 */
public class Lwjgl3Clipboard implements Clipboard {
    @Override
    public boolean hasContents() {
        String contents = getContents();
        return contents != null && !contents.isEmpty();
    }

    @Override
    public String getContents() {
        return GLFW.glfwGetClipboardString(((Lwjgl3Graphics) Kerman.graphics).getWindow().getWindowHandle());
    }

    @Override
    public void setContents(@NotNull String content) {
        GLFW.glfwSetClipboardString(((Lwjgl3Graphics) Kerman.graphics).getWindow().getWindowHandle(), content);
    }
}
