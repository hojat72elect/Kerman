package com.kerman.backends.lwjgl3

import com.kerman.core.Application
import com.kerman.backends.lwjgl3.audio.Lwjgl3Audio

interface Lwjgl3ApplicationBase : Application {
    fun createAudio(config: Lwjgl3ApplicationConfiguration): Lwjgl3Audio
    fun createInput(window: Lwjgl3Window): Lwjgl3Input
}
