package com.kerman.core.audio

import com.kerman.core.utils.Disposable

/**
 * Info : This interface was inspired by "com.badlogic.gdx.audio.AudioRecorder".
 *
 * An AudioRecorder allows to record input from an audio device. It has a sampling rate and is either stereo or mono. Samples are
 * returned in signed 16-bit PCM format. Stereo samples are interleaved in the order left channel, right channel. The
 * AudioRecorder has to be disposed if no longer needed via the [dispose].
 */
interface AudioRecorder : Disposable {

    /**
     * Reads in [numSamples] samples into the array [samples] starting at [offset]. If the recorder is in stereo you have to multiply
     * [numSamples] by 2.
     *
     * @param samples the array to write the samples to.
     * @param offset the offset into the array.
     * @param numSamples the number of samples to be read.
     */
    fun read(samples: ShortArray, offset: Int, numSamples: Int)

    /**
     * Disposes the AudioRecorder.
     */
    override fun dispose()
}
