package com.kerman.core.audio

import com.kerman.core.utils.Disposable

/**
 * This interface was inspired by "com.badlogic.gdx.audio.AudioDevice".
 *
 * Encapsulates an audio device in mono or stereo mode. Use the [writeSamples] functions to write float or 16-bit signed short PCM data directly to the audio device.
 * Stereo samples are interleaved in the order left channel sample, right channel sample. The [dispose] function must be
 * called when this AudioDevice is no longer needed.
 */
interface AudioDevice : Disposable {

    /**
     * @return whether this AudioDevice is in mono or stereo mode.
     */
    fun isMono(): Boolean

    /**
     * Writes the array of 16-bit signed PCM samples to the audio device and blocks until they have been processed.
     *
     * @param samples    The samples.
     * @param offset     The offset into the samples array
     * @param numSamples the number of samples to write to the device
     */
    fun writeSamples(samples: ShortArray, offset: Int, numSamples: Int)

    /**
     * Writes the array of float PCM samples to the audio device and blocks until they have been processed.
     *
     * @param samples    The samples.
     * @param offset     The offset into the samples array
     * @param numSamples the number of samples to write to the device
     */
    fun writeSamples(samples: FloatArray, offset: Int, numSamples: Int)

    /**
     * @return the latency in samples.
     */
    fun getLatency(): Int

    /**
     * Frees all resources associated with this AudioDevice. Needs to be called when the device is no longer needed.
     */
    override fun dispose()

    /**
     * Sets the volume in the range [0,1].
     */
    fun setVolume(volume: Float)

    /**
     * Pauses the audio device if supported.
     */
    fun pause()

    /**
     * Unpauses the audio device if supported.
     */
    fun resume()
}
