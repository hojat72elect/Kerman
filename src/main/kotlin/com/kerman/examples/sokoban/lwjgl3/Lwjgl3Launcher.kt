package com.kerman.examples.sokoban.lwjgl3

import com.kerman.examples.sokoban.SokobanGame
import com.kerman.backends.lwjgl3.Lwjgl3Application
import com.kerman.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/**
 * Launches the desktop (LWJGL3) application.
 */
fun main() {
    if (StartupHelper.Companion.startNewJvmIfRequired()) return  // This handles macOS support and helps on Windows.

    val configuration = Lwjgl3ApplicationConfiguration()
    configuration.setTitle("NewSokoban")


    // Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional refresh rates.
    configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1)

    configuration.setWindowedMode(640, 480)
    configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")

    Lwjgl3Application(SokobanGame(), configuration)
}
