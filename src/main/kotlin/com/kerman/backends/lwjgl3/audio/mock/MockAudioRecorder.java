package com.kerman.backends.lwjgl3.audio.mock;

import com.kerman.core.audio.AudioRecorder;
import org.jetbrains.annotations.NotNull;

/**
 * The headless backend does its best to mock elements. This is intended to make code-sharing between server and client as simple
 * as possible.
 */
public class MockAudioRecorder implements AudioRecorder {

    @Override
    public void read(@NotNull short[] samples, int offset, int numSamples) {

    }

    @Override
    public void dispose() {

    }
}
