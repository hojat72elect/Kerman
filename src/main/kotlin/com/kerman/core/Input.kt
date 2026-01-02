package com.kerman.core

import com.kerman.core.utils.ObjectIntMap
import com.kerman.core.input.NativeInputConfiguration

/**
 * Info : This interface was inspired by "com.badlogic.gdx.Input".
 *
 * Interface to the input facilities. This allows polling the state of the keyboard, the touch screen and the accelerometer. On
 * some backends (desktop, etc) the touch screen is replaced by mouse input. The accelerometer is of course not available on
 * all backends.
 * Instead of polling for events, one can process all input events with an [InputProcessor]. You can set the InputProcessor
 * via the [.setInputProcessor] method. It will be called before the [ApplicationListener.render]
 * method in each frame.
 * Keyboard keys are translated to the constants in [Keys] transparently on all systems. Do not use system specific key
 * constants.
 * The class also offers methods to use (and test for the presence of) other input systems like vibration, compass, on-screen
 * keyboards, and cursor capture. Support for simple input dialogs is also provided.
 */
interface Input {

    /**
     * @return The acceleration force in m/s^2 applied to the device in the X axis, including the force of gravity
     */
    fun getAccelerometerX(): Float

    /**
     * @return The acceleration force in m/s^2 applied to the device in the Y axis, including the force of gravity
     */
    fun getAccelerometerY(): Float

    /**
     * @return The acceleration force in m/s^2 applied to the device in the Z axis, including the force of gravity
     */
    fun getAccelerometerZ(): Float

    /**
     * @return The rate of rotation in rad/s around the X axis
     */
    fun getGyroscopeX(): Float

    /**
     * @return The rate of rotation in rad/s around the Y axis
     */
    fun getGyroscopeY(): Float

    /**
     * @return The rate of rotation in rad/s around the Z axis
     */
    fun getGyroscopeZ(): Float

    /**
     * @return The maximum number of pointers supported
     */
    fun getMaxPointers(): Int

    /**
     * @return The x coordinate of the last touch on touch screen devices and the current mouse position on desktop for the first
     * pointer in screen coordinates. The screen origin is the top left corner.
     */
    fun getX(): Int

    /**
     * Returns the x coordinate in screen coordinates of the given pointer. Pointers are indexed from 0 to n. The pointer id
     * identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the x coordinate
     */
    fun getX(pointer: Int): Int

    /**
     * @return the different between the current pointer location and the last pointer location on the x-axis.
     */
    fun getDeltaX(): Int

    /**
     * @return the different between the current pointer location and the last pointer location on the x-axis.
     */
    fun getDeltaX(pointer: Int): Int

    /**
     * @return The y coordinate of the last touch on touch screen devices and the current mouse position on desktop for the first
     * pointer in screen coordinates. The screen origin is the top left corner.
     */
    fun getY(): Int

    /**
     * Returns the y coordinate in screen coordinates of the given pointer. Pointers are indexed from 0 to n. The pointer id
     * identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the y coordinate
     */
    fun getY(pointer: Int): Int

    /**
     * @return the different between the current pointer location and the last pointer location on the y-axis.
     */
    fun getDeltaY(): Int

    /**
     * @return the different between the current pointer location and the last pointer location on the y-axis.
     */
    fun getDeltaY(pointer: Int): Int

    /**
     * @return whether the screen is currently touched.
     */
    fun isTouched(): Boolean

    /**
     * @return whether a new touch down event just occurred.
     */
    fun justTouched(): Boolean

    /**
     * Whether the screen is currently touched by the pointer with the given index. Pointers are indexed from 0 to n. The pointer
     * id identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer
     * @return whether the screen is touched by the pointer
     */
    fun isTouched(pointer: Int): Boolean

    /**
     * @return the pressure of the first pointer
     */
    fun getPressure(): Float

    /**
     * Returns the pressure of the given pointer, where 0 is untouched. On Android it should be up to 1.0, but it can go above
     * that slightly and its not consistent between devices. On iOS 1.0 is the normal touch and significantly more of hard touch.
     * Check relevant manufacturer documentation for details. Check availability with
     * [Input.isPeripheralAvailable]. If not supported, returns 1.0 when touched.
     *
     * @param pointer the pointer id.
     * @return the pressure
     */
    fun getPressure(pointer: Int): Float

    /**
     * Whether a given button is pressed or not. Button constants can be found in [Buttons]. On Android only the
     * Buttons#LEFT constant is meaningful before version 4.0.
     *
     * @param button the button to check.
     * @return whether the button is down or not.
     */
    fun isButtonPressed(button: Int): Boolean

    /**
     * Returns whether a given button has just been pressed. Button constants can be found in [Buttons]. On Android only the
     * Buttons#LEFT constant is meaningful before version 4.0.
     *
     * @param button the button to check.
     * @return true or false.
     */
    fun isButtonJustPressed(button: Int): Boolean

    /**
     * Returns whether the key is pressed.
     *
     * @param key The key code as found in [Input.Keys].
     * @return true or false.
     */
    fun isKeyPressed(key: Int): Boolean

    /**
     * Returns whether the key has just been pressed.
     *
     * @param key The key code as found in [Input.Keys].
     * @return true or false.
     */
    fun isKeyJustPressed(key: Int): Boolean

    /**
     * System dependent method to input a string of text. A dialog box will be created with the given title and the given text as
     * a message for the user. Will use the Default keyboard type. Once the dialog has been closed the provided
     * [TextInputListener] will be called on the rendering thread.
     *
     * @param listener The TextInputListener.
     * @param title    The title of the text input dialog.
     * @param text     The message presented to the user.
     */
    fun getTextInput(listener: TextInputListener, title: String, text: String, hint: String)

    /**
     * System dependent method to input a string of text. A dialog box will be created with the given title and the given text as
     * a message for the user. Once the dialog has been closed the provided [TextInputListener] will be called on the
     * rendering thread.
     *
     * @param listener The TextInputListener.
     * @param title    The title of the text input dialog.
     * @param text     The message presented to the user.
     * @param type     which type of keyboard we wish to display
     */
    fun getTextInput(
        listener: TextInputListener,
        title: String,
        text: String,
        hint: String,
        type: OnscreenKeyboardType
    )

    /**
     * Sets the on-screen keyboard visible if available. Will use the Default keyboard type.
     *
     * @param visible visible or not
     */
    fun setOnscreenKeyboardVisible(visible: Boolean)

    /**
     * Sets the on-screen keyboard visible if available.
     *
     * @param visible visible or not
     * @param type    which type of keyboard we wish to display. Can be null when hiding
     */
    fun setOnscreenKeyboardVisible(visible: Boolean, type: OnscreenKeyboardType)

    /**
     * Sets the on-screen keyboard visible if available.
     *
     * @param configuration The configuration for the native input field
     */
    fun openTextInputField(configuration: NativeInputConfiguration)

    /**
     * Closes the native input field and applies the result to the input wrapper.
     *
     * @param sendReturn Whether a "return" key should be send after processing
     */
    fun closeTextInputField(sendReturn: Boolean)

    /**
     * This will set a keyboard height callback. This will get called, whenever the keyboard height changes. Note: When using
     * openTextInputField, it will report the height of the native input field too.
     */
    fun setKeyboardHeightObserver(observer: KeyboardHeightObserver)

    /**
     * Generates a simple haptic effect of a given duration or a vibration effect on devices without haptic capabilities. Note
     * that on Android backend you'll need the permission
     * ` <uses-permission android:name="android.permission.VIBRATE" />` in your manifest file in order for this to work.
     * On iOS backend you'll need to set `useHaptics = true` for devices with haptics capabilities to use them.
     *
     * @param milliseconds the number of milliseconds to vibrate.
     */
    fun vibrate(milliseconds: Int)

    /**
     * Generates a simple haptic effect of a given duration and default amplitude. Note that on Android backend you'll need the
     * permission ` <uses-permission android:name="android.permission.VIBRATE" />` in your manifest file in order for
     * this to work. On iOS backend you'll need to set `useHaptics = true` for devices with haptics capabilities to use
     * them.
     *
     * @param milliseconds the duration of the haptics effect
     * @param fallback     whether to use non-haptic vibrator on devices without haptics capabilities (or haptics disabled). Fallback
     * non-haptic vibrations may ignore length parameter in some backends.
     */
    fun vibrate(milliseconds: Int, fallback: Boolean)

    /**
     * Generates a simple haptic effect of a given duration and amplitude. Note that on Android backend you'll need the permission
     * ` <uses-permission android:name="android.permission.VIBRATE" />` in your manifest file in order for this to work.
     * On iOS backend you'll need to set `useHaptics = true` for devices with haptics capabilities to use them.
     *
     * @param milliseconds the duration of the haptics effect
     * @param amplitude    the amplitude/strength of the haptics effect. Valid values in the range [0, 255].
     * @param fallback     whether to use non-haptic vibrator on devices without haptics capabilities (or haptics disabled). Fallback
     * non-haptic vibrations may ignore length and/or amplitude parameters in some backends.
     */
    fun vibrate(milliseconds: Int, amplitude: Int, fallback: Boolean)

    /**
     * Generates a simple haptic effect of a type. VibrationTypes are length/amplitude haptic effect presets that depend on each
     * device and are defined by manufacturers. Should give most consistent results across devices and OSs. Note that on Android
     * backend you'll need the permission ` <uses-permission android:name="android.permission.VIBRATE" />` in your
     * manifest file in order for this to work. On iOS backend you'll need to set `useHaptics = true` for devices with
     * haptics capabilities to use them.
     *
     * @param vibrationType the type of vibration
     */
    fun vibrate(vibrationType: VibrationType)

    /**
     * The azimuth is the angle of the device's orientation around the z-axis. The positive z-axis points towards the earths
     * center.
     *
     * @return the azimuth in degrees
     * @see [http://developer.android.com/reference/android/hardware/SensorManager.html.getRotationMatrix](http://developer.android.com/reference/android/hardware/SensorManager.html#getRotationMatrix(float[], float[], float[], float[]))
     */
    fun getAzimuth(): Float

    /**
     * The pitch is the angle of the device's orientation around the x-axis. The positive x-axis roughly points to the west and is
     * orthogonal to the z- and y-axis.
     *
     * @return the pitch in degrees
     * @see [http://developer.android.com/reference/android/hardware/SensorManager.html.getRotationMatrix](http://developer.android.com/reference/android/hardware/SensorManager.html#getRotationMatrix(float[], float[], float[], float[]))
     */
    fun getPitch(): Float

    /**
     * The roll is the angle of the device's orientation around the y-axis. The positive y-axis points to the magnetic north pole
     * of the earth.
     *
     * @return the roll in degrees
     * @see [http://developer.android.com/reference/android/hardware/SensorManager.html.getRotationMatrix](http://developer.android.com/reference/android/hardware/SensorManager.html#getRotationMatrix(float[], float[], float[], float[]))
     */
    fun getRoll(): Float

    /**
     * Returns the rotation matrix describing the devices rotation as per
     * [SensorManager#getRotationMatrix(float[], float[], float[], float[])](http://developer.android.com/reference/android/hardware/SensorManager.html#getRotationMatrix(float[], float[], float[], float[])) . Does not manipulate the matrix
     * if the platform does not have an accelerometer.
     *
     * @param matrix
     */
    fun getRotationMatrix(matrix: FloatArray)

    /**
     * @return the time of the event currently reported to the [InputProcessor].
     */
    fun getCurrentEventTime(): Long

    /**
     * Sets whether the given key on Android should be caught. No effect on other platforms. All keys that are not caught
     * may be handled by other apps or background processes on Android. For
     * example, media or volume buttons are handled by background media players if present, or Space key triggers a scroll. All
     * keys you need to control your game should be caught to prevent unintended behaviour.
     *
     * @param keycode  keycode to catch
     * @param catchKey whether to catch the given keycode
     */
    fun setCatchKey(keycode: Int, catchKey: Boolean)

    /**
     * @param keycode keycode to check if caught
     * @return true if the given keycode is configured to be caught
     */
    fun isCatchKey(keycode: Int): Boolean

    /**
     * @return the currently set [InputProcessor] or null.
     */
    fun getInputProcessor(): InputProcessor?

    /**
     * Sets the [InputProcessor] that will receive all touch and key input events. It will be called before the
     * [ApplicationListener.render] method each frame.
     *
     * @param processor the InputProcessor
     */
    fun setInputProcessor(processor: InputProcessor?)

    /**
     * Queries whether a [Peripheral] is currently available. In case of Android and the [Peripheral.HardwareKeyboard]
     * this returns the whether the keyboard is currently slid out or not.
     *
     * @param peripheral the [Peripheral]
     * @return whether the peripheral is available or not.
     */
    fun isPeripheralAvailable(peripheral: Peripheral): Boolean

    /**
     * @return the rotation of the device with respect to its native orientation.
     */
    fun getRotation(): Int

    /**
     * @return the native orientation of the device.
     */
    fun getNativeOrientation(): Orientation

    /**
     * @return whether the mouse cursor is catched.
     */
    fun isCursorCatched(): Boolean

    /**
     * Only viable on the desktop. Will confine the mouse cursor location to the window and hide the mouse cursor. X and y
     * coordinates are still reported as if the mouse was not catched.
     *
     * @param catched whether to catch or not to catch the mouse cursor
     */
    fun setCursorCatched(catched: Boolean)

    /**
     * Only viable on the desktop. Will set the mouse cursor location to the given window coordinates (origin top-left corner).
     *
     * @param x the x-position
     * @param y the y-position
     */
    fun setCursorPosition(x: Int, y: Int)

    /**
     * Enumeration of potentially available peripherals. Use with [Input.isPeripheralAvailable].
     */
    enum class Peripheral {
        HardwareKeyboard, OnscreenKeyboard, MultitouchScreen, Accelerometer, Compass, Vibrator, HapticFeedback, Gyroscope, RotationVector, Pressure
    }

    enum class OnscreenKeyboardType {
        Default, NumberPad, PhonePad, Email, Password, URI
    }

    enum class VibrationType {
        LIGHT, MEDIUM, HEAVY
    }

    enum class Orientation {
        Landscape, Portrait
    }

    /**
     * Callback interface for [Input.getTextInput]
     */
    interface TextInputListener {
        fun input(text: String)
        fun canceled()
    }

    interface InputStringValidator {
        /**
         * @param toCheck The string that should be validated
         * @return true, if the string is acceptable, false if not.
         */
        fun validate(toCheck: String): Boolean
    }

    interface KeyboardHeightObserver {
        fun onKeyboardHeightChanged(height: Int)
    }

    /**
     * Mouse buttons.
     */
    object Buttons {
        const val LEFT = 0
        const val RIGHT = 1
        const val MIDDLE = 2
        const val BACK = 3
        const val FORWARD = 4
    }

    /**
     * Keys.
     */
    object Keys {
        const val ANY_KEY = -1
        const val NUM_0 = 7
        const val NUM_1 = 8
        const val NUM_2 = 9
        const val NUM_3 = 10
        const val NUM_4 = 11
        const val NUM_5 = 12
        const val NUM_6 = 13
        const val NUM_7 = 14
        const val NUM_8 = 15
        const val NUM_9 = 16
        const val A = 29
        const val ALT_LEFT = 57
        const val ALT_RIGHT = 58
        const val APOSTROPHE = 75
        const val AT = 77
        const val B = 30
        const val BACK = 4
        const val BACKSLASH = 73
        const val C = 31
        const val CALL = 5
        const val CAMERA = 27
        const val CAPS_LOCK = 115
        const val CLEAR = 28
        const val COMMA = 55
        const val D = 32
        const val DEL = 67
        const val BACKSPACE = 67
        const val FORWARD_DEL = 112
        const val DPAD_CENTER = 23
        const val DPAD_DOWN = 20
        const val DPAD_LEFT = 21
        const val DPAD_RIGHT = 22
        const val DPAD_UP = 19
        const val CENTER = 23
        const val DOWN = 20
        const val LEFT = 21
        const val RIGHT = 22
        const val UP = 19
        const val E = 33
        const val ENDCALL = 6
        const val ENTER = 66
        const val ENVELOPE = 65
        const val EQUALS = 70
        const val EXPLORER = 64
        const val F = 34
        const val FOCUS = 80
        const val G = 35
        const val GRAVE = 68
        const val H = 36
        const val HEADSETHOOK = 79
        const val HOME = 3
        const val I = 37
        const val J = 38
        const val K = 39
        const val L = 40
        const val LEFT_BRACKET = 71
        const val M = 41
        const val MEDIA_FAST_FORWARD = 90
        const val MEDIA_NEXT = 87
        const val MEDIA_PLAY_PAUSE = 85
        const val MEDIA_PREVIOUS = 88
        const val MEDIA_REWIND = 89
        const val MEDIA_STOP = 86
        const val MENU = 82
        const val MINUS = 69
        const val MUTE = 91
        const val N = 42
        const val NOTIFICATION = 83
        const val NUM = 78
        const val O = 43
        const val P = 44
        const val PAUSE = 121 // aka break
        const val PERIOD = 56
        const val PLUS = 81
        const val POUND = 18
        const val POWER = 26
        const val PRINT_SCREEN = 120 // aka SYSRQ
        const val Q = 45
        const val R = 46
        const val RIGHT_BRACKET = 72
        const val S = 47
        const val SCROLL_LOCK = 116
        const val SEARCH = 84
        const val SEMICOLON = 74
        const val SHIFT_LEFT = 59
        const val SHIFT_RIGHT = 60
        const val SLASH = 76
        const val SOFT_LEFT = 1
        const val SOFT_RIGHT = 2
        const val SPACE = 62
        const val STAR = 17
        const val SYM = 63 // on MacOS, this is Command (⌘)
        const val T = 48
        const val TAB = 61
        const val U = 49
        const val UNKNOWN = 0
        const val V = 50
        const val VOLUME_DOWN = 25
        const val VOLUME_UP = 24
        const val W = 51
        const val X = 52
        const val Y = 53
        const val Z = 54
        const val META_ALT_LEFT_ON = 16
        const val META_ALT_ON = 2
        const val META_ALT_RIGHT_ON = 32
        const val META_SHIFT_LEFT_ON = 64
        const val META_SHIFT_ON = 1
        const val META_SHIFT_RIGHT_ON = 128
        const val META_SYM_ON = 4
        const val CONTROL_LEFT = 129
        const val CONTROL_RIGHT = 130
        const val ESCAPE = 111
        const val END = 123
        const val INSERT = 124
        const val PAGE_UP = 92
        const val PAGE_DOWN = 93
        const val PICTSYMBOLS = 94
        const val SWITCH_CHARSET = 95
        const val BUTTON_CIRCLE = 255
        const val BUTTON_A = 96
        const val BUTTON_B = 97
        const val BUTTON_C = 98
        const val BUTTON_X = 99
        const val BUTTON_Y = 100
        const val BUTTON_Z = 101
        const val BUTTON_L1 = 102
        const val BUTTON_R1 = 103
        const val BUTTON_L2 = 104
        const val BUTTON_R2 = 105
        const val BUTTON_THUMBL = 106
        const val BUTTON_THUMBR = 107
        const val BUTTON_START = 108
        const val BUTTON_SELECT = 109
        const val BUTTON_MODE = 110

        const val NUMPAD_0 = 144
        const val NUMPAD_1 = 145
        const val NUMPAD_2 = 146
        const val NUMPAD_3 = 147
        const val NUMPAD_4 = 148
        const val NUMPAD_5 = 149
        const val NUMPAD_6 = 150
        const val NUMPAD_7 = 151
        const val NUMPAD_8 = 152
        const val NUMPAD_9 = 153

        const val NUMPAD_DIVIDE = 154
        const val NUMPAD_MULTIPLY = 155
        const val NUMPAD_SUBTRACT = 156
        const val NUMPAD_ADD = 157
        const val NUMPAD_DOT = 158
        const val NUMPAD_COMMA = 159
        const val NUMPAD_ENTER = 160
        const val NUMPAD_EQUALS = 161
        const val NUMPAD_LEFT_PAREN = 162
        const val NUMPAD_RIGHT_PAREN = 163
        const val NUM_LOCK = 143

        const val WORLD_1 = 240
        const val WORLD_2 = 241

        // public static final int BACKTICK = 0;
        // public static final int TILDE = 0;
        // public static final int UNDERSCORE = 0;
        // public static final int DOT = 0;
        // public static final int BREAK = 0;
        // public static final int PIPE = 0;
        // public static final int EXCLAMATION = 0;
        // public static final int QUESTIONMARK = 0;
        // ` | VK_BACKTICK
        // ~ | VK_TILDE
        // : | VK_COLON
        // _ | VK_UNDERSCORE
        // . | VK_DOT
        // (break) | VK_BREAK
        // | | VK_PIPE
        // ! | VK_EXCLAMATION
        // ? | VK_QUESTION
        const val COLON = 243
        const val F1 = 131
        const val F2 = 132
        const val F3 = 133
        const val F4 = 134
        const val F5 = 135
        const val F6 = 136
        const val F7 = 137
        const val F8 = 138
        const val F9 = 139
        const val F10 = 140
        const val F11 = 141
        const val F12 = 142
        const val F13 = 183
        const val F14 = 184
        const val F15 = 185
        const val F16 = 186
        const val F17 = 187
        const val F18 = 188
        const val F19 = 189
        const val F20 = 190
        const val F21 = 191
        const val F22 = 192
        const val F23 = 193
        const val F24 = 194

        const val MAX_KEYCODE = 255
        private var keyNames: ObjectIntMap<String>? = null

        /**
         * @return a human readable representation of the keycode. The returned value can be used in
         * [Input.Keys.valueOf]
         */
        @JvmStatic
        fun toString(keycode: Int): String? {
            require(keycode >= 0) { "keycode cannot be negative, keycode: $keycode" }
            require(keycode <= MAX_KEYCODE) { "keycode cannot be greater than 255, keycode: $keycode" }
            return when (keycode) {
                UNKNOWN -> "Unknown"
                SOFT_LEFT -> "Soft Left"
                SOFT_RIGHT -> "Soft Right"
                HOME -> "Home"
                BACK -> "Back"
                CALL -> "Call"
                ENDCALL -> "End Call"
                NUM_0 -> "0"
                NUM_1 -> "1"
                NUM_2 -> "2"
                NUM_3 -> "3"
                NUM_4 -> "4"
                NUM_5 -> "5"
                NUM_6 -> "6"
                NUM_7 -> "7"
                NUM_8 -> "8"
                NUM_9 -> "9"
                STAR -> "*"
                POUND -> "#"
                UP -> "Up"
                DOWN -> "Down"
                LEFT -> "Left"
                RIGHT -> "Right"
                CENTER -> "Center"
                VOLUME_UP -> "Volume Up"
                VOLUME_DOWN -> "Volume Down"
                POWER -> "Power"
                CAMERA -> "Camera"
                CLEAR -> "Clear"
                A -> "A"
                B -> "B"
                C -> "C"
                D -> "D"
                E -> "E"
                F -> "F"
                G -> "G"
                H -> "H"
                I -> "I"
                J -> "J"
                K -> "K"
                L -> "L"
                M -> "M"
                N -> "N"
                O -> "O"
                P -> "P"
                Q -> "Q"
                R -> "R"
                S -> "S"
                T -> "T"
                U -> "U"
                V -> "V"
                W -> "W"
                X -> "X"
                Y -> "Y"
                Z -> "Z"
                COMMA -> ","
                PERIOD -> "."
                ALT_LEFT -> "L-Alt"
                ALT_RIGHT -> "R-Alt"
                SHIFT_LEFT -> "L-Shift"
                SHIFT_RIGHT -> "R-Shift"
                TAB -> "Tab"
                SPACE -> "Space"
                SYM -> "SYM"
                EXPLORER -> "Explorer"
                ENVELOPE -> "Envelope"
                ENTER -> "Enter"
                DEL -> "Delete"
                GRAVE -> "`"
                MINUS -> "-"
                EQUALS -> "="
                LEFT_BRACKET -> "["
                RIGHT_BRACKET -> "]"
                BACKSLASH -> "\\"
                SEMICOLON -> ";"
                APOSTROPHE -> "'"
                SLASH -> "/"
                AT -> "@"
                NUM -> "Num"
                HEADSETHOOK -> "Headset Hook"
                FOCUS -> "Focus"
                PLUS -> "Plus"
                MENU -> "Menu"
                NOTIFICATION -> "Notification"
                SEARCH -> "Search"
                MEDIA_PLAY_PAUSE -> "Play/Pause"
                MEDIA_STOP -> "Stop Media"
                MEDIA_NEXT -> "Next Media"
                MEDIA_PREVIOUS -> "Prev Media"
                MEDIA_REWIND -> "Rewind"
                MEDIA_FAST_FORWARD -> "Fast Forward"
                MUTE -> "Mute"
                PAGE_UP -> "Page Up"
                PAGE_DOWN -> "Page Down"
                PICTSYMBOLS -> "PICTSYMBOLS"
                SWITCH_CHARSET -> "SWITCH_CHARSET"
                BUTTON_A -> "A Button"
                BUTTON_B -> "B Button"
                BUTTON_C -> "C Button"
                BUTTON_X -> "X Button"
                BUTTON_Y -> "Y Button"
                BUTTON_Z -> "Z Button"
                BUTTON_L1 -> "L1 Button"
                BUTTON_R1 -> "R1 Button"
                BUTTON_L2 -> "L2 Button"
                BUTTON_R2 -> "R2 Button"
                BUTTON_THUMBL -> "Left Thumb"
                BUTTON_THUMBR -> "Right Thumb"
                BUTTON_START -> "Start"
                BUTTON_SELECT -> "Select"
                BUTTON_MODE -> "Button Mode"
                FORWARD_DEL -> "Forward Delete"
                CONTROL_LEFT -> "L-Ctrl"
                CONTROL_RIGHT -> "R-Ctrl"
                ESCAPE -> "Escape"
                END -> "End"
                INSERT -> "Insert"
                NUMPAD_0 -> "Numpad 0"
                NUMPAD_1 -> "Numpad 1"
                NUMPAD_2 -> "Numpad 2"
                NUMPAD_3 -> "Numpad 3"
                NUMPAD_4 -> "Numpad 4"
                NUMPAD_5 -> "Numpad 5"
                NUMPAD_6 -> "Numpad 6"
                NUMPAD_7 -> "Numpad 7"
                NUMPAD_8 -> "Numpad 8"
                NUMPAD_9 -> "Numpad 9"
                COLON -> ":"
                F1 -> "F1"
                F2 -> "F2"
                F3 -> "F3"
                F4 -> "F4"
                F5 -> "F5"
                F6 -> "F6"
                F7 -> "F7"
                F8 -> "F8"
                F9 -> "F9"
                F10 -> "F10"
                F11 -> "F11"
                F12 -> "F12"
                F13 -> "F13"
                F14 -> "F14"
                F15 -> "F15"
                F16 -> "F16"
                F17 -> "F17"
                F18 -> "F18"
                F19 -> "F19"
                F20 -> "F20"
                F21 -> "F21"
                F22 -> "F22"
                F23 -> "F23"
                F24 -> "F24"
                NUMPAD_DIVIDE -> "Num /"
                NUMPAD_MULTIPLY -> "Num *"
                NUMPAD_SUBTRACT -> "Num -"
                NUMPAD_ADD -> "Num +"
                NUMPAD_DOT -> "Num ."
                NUMPAD_COMMA -> "Num ,"
                NUMPAD_ENTER -> "Num Enter"
                NUMPAD_EQUALS -> "Num ="
                NUMPAD_LEFT_PAREN -> "Num ("
                NUMPAD_RIGHT_PAREN -> "Num )"
                NUM_LOCK -> "Num Lock"
                CAPS_LOCK -> "Caps Lock"
                SCROLL_LOCK -> "Scroll Lock"
                PAUSE -> "Pause"
                PRINT_SCREEN -> "Print"
                else -> null
            }
        }

        /**
         * @param keyname the keyname returned by the [Keys.toString] method
         * @return the int keycode
         */
        @JvmStatic
        fun valueOf(keyname: String): Int {
            if (keyNames == null) initializeKeyNames()
            return keyNames!!.get(keyname, -1)
        }

        /**
         * lazily initialized in [Keys.valueOf]
         */
        private fun initializeKeyNames() {
            keyNames = ObjectIntMap()
            for (i in 0..255) {
                val name = toString(i)
                if (name != null) keyNames!!.put(name, i)
            }
        }
    }
}
