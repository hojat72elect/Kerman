package com.kerman.backends.lwjgl3.audio.mock;

import com.kerman.core.audio.AudioDevice;
import com.kerman.core.audio.AudioRecorder;
import com.kerman.core.audio.Music;
import com.kerman.core.audio.Sound;
import com.kerman.backends.lwjgl3.audio.Lwjgl3Audio;
import com.kerman.core.files.FileHandle;
import org.jetbrains.annotations.NotNull;

/**
 * The headless backend does its best to mock elements. This is intended to make code-sharing between server and client as simple
 * as possible.
 */
public class MockAudio implements Lwjgl3Audio {

    @Override
    public AudioDevice newAudioDevice(int samplingRate, boolean isMono) {
        return new MockAudioDevice();
    }

    @Override
    public AudioRecorder newAudioRecorder(int samplingRate, boolean isMono) {
        return new MockAudioRecorder();
    }

    @Override
    public Sound newSound(@NotNull FileHandle fileHandle) {
        return new MockSound();
    }

    @Override
    public Music newMusic(@NotNull FileHandle file) {
        return new MockMusic();
    }

    @Override
    public boolean switchOutputDevice(String deviceIdentifier) {
        return true;
    }

    @NotNull
    @Override
    public String[] getAvailableOutputDevices() {
        return new String[0];
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }
}
