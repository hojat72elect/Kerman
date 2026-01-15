package com.kerman.core.scenes.scene2d.utils;

import com.kerman.core.Kerman;
import com.kerman.core.Input.Buttons;
import com.kerman.core.Input.Keys;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

/**
 * Info : This class was inspired by "com.badlogic.gdx.scenes.scene2d.utils.UIUtils".
 */
public final class UIUtils {
    static public boolean isAndroid = SharedLibraryLoader.os == Os.Android;
    static public boolean isMac = SharedLibraryLoader.os == Os.MacOsX;
    static public boolean isWindows = SharedLibraryLoader.os == Os.Windows;
    static public boolean isLinux = SharedLibraryLoader.os == Os.Linux;
    static public boolean isIos = SharedLibraryLoader.os == Os.IOS;
    private UIUtils() {
    }

    static public boolean left() {
        return Kerman.input.isButtonPressed(Buttons.LEFT);
    }

    static public boolean left(int button) {
        return button == Buttons.LEFT;
    }

    static public boolean right() {
        return Kerman.input.isButtonPressed(Buttons.RIGHT);
    }

    static public boolean right(int button) {
        return button == Buttons.RIGHT;
    }

    static public boolean middle() {
        return Kerman.input.isButtonPressed(Buttons.MIDDLE);
    }

    static public boolean middle(int button) {
        return button == Buttons.MIDDLE;
    }

    static public boolean shift() {
        return Kerman.input.isKeyPressed(Keys.SHIFT_LEFT) || Kerman.input.isKeyPressed(Keys.SHIFT_RIGHT);
    }

    static public boolean shift(int keycode) {
        return keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT;
    }

    static public boolean ctrl() {
        if (isMac)
            return Kerman.input.isKeyPressed(Keys.SYM);
        else
            return Kerman.input.isKeyPressed(Keys.CONTROL_LEFT) || Kerman.input.isKeyPressed(Keys.CONTROL_RIGHT);
    }

    static public boolean ctrl(int keycode) {
        if (isMac)
            return keycode == Keys.SYM;
        else
            return keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT;
    }

    static public boolean alt() {
        return Kerman.input.isKeyPressed(Keys.ALT_LEFT) || Kerman.input.isKeyPressed(Keys.ALT_RIGHT);
    }

    static public boolean alt(int keycode) {
        return keycode == Keys.ALT_LEFT || keycode == Keys.ALT_RIGHT;
    }
}
