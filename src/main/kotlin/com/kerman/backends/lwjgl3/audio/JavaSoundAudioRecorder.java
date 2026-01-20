package com.kerman.backends.lwjgl3.audio;

import com.kerman.core.audio.AudioRecorder;
import com.kerman.core.utils.KermanRuntimeException;

import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class JavaSoundAudioRecorder implements AudioRecorder {
    private final TargetDataLine line;
    private byte[] buffer = new byte[1024 * 4];

    public JavaSoundAudioRecorder(int samplingRate, boolean isMono) {
        try {
            AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, samplingRate, 16, isMono ? 1 : 2, isMono ? 2 : 4, samplingRate,
                    false);
            line = AudioSystem.getTargetDataLine(format);
            line.open(format, buffer.length);
            line.start();
        } catch (Exception ex) {
            throw new KermanRuntimeException("Error creating JavaSoundAudioRecorder.", ex);
        }
    }

    public void read(@NotNull short[] samples, int offset, int numSamples) {
        if (buffer.length < numSamples * 2) buffer = new byte[numSamples * 2];

        int toRead = numSamples * 2;
        int read = 0;
        while (read != toRead)
            read += line.read(buffer, read, toRead - read);

        for (int i = 0, j = 0; i < numSamples * 2; i += 2, j++)
            samples[offset + j] = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xff));
    }

    public void dispose() {
        line.close();
    }
}
