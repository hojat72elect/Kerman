package com.kerman.core.audio

import com.kerman.core.utils.Disposable
import com.kerman.core.Audio

/**
 * This interface was inspired by "com.badlogic.gdx.audio.Sound".
 *
 * A Sound is a short audio clip that can be played numerous times in parallel. It's completely loaded into memory so only load
 * small audio files. Call the [.dispose] method when you're done using the Sound.
 * Sound instances are created via a call to [Audio.newSound].
 * Calling the [.play()] or [.play(Float)] method will return a long which is an id to that instance of the sound. You
 * can use this id to modify the playback of that sound instance.
 * **Note**: any values provided will not be clamped, it is the developer's responsibility to do so.
 */
interface Sound : Disposable {

    /**
     * Plays the sound. If the sound is already playing, it will be played again, concurrently.
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun play(): Long

    /**
     * Plays the sound. If the sound is already playing, it will be played again, concurrently.
     * @param volume the volume in the range [0,1]
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun play(volume: Float): Long

    /**
     * Plays the sound. If the sound is already playing, it will be played again, concurrently. Note that (with the exception of
     * the web backend) panning only works for mono sounds, not for stereo sounds!
     * @param volume the volume in the range [0,1]
     * @param pitch  the pitch multiplier, 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
     * @param pan    panning in the range -1 (full left) to 1 (full right). 0 is center position.
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun play(volume: Float, pitch: Float, pan: Float): Long

    /**
     * Plays the sound, looping. If the sound is already playing, it will be played again, concurrently.
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun loop(): Long

    /**
     * Plays the sound, looping. If the sound is already playing, it will be played again, concurrently. You need to stop the
     * sound via a call to [.stop] using the returned id.
     * @param volume the volume in the range [0, 1]
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun loop(volume: Float): Long

    /**
     * Plays the sound, looping. If the sound is already playing, it will be played again, concurrently. You need to stop the
     * sound via a call to [.stop] using the returned id. Note that (with the exception of the web backend) panning
     * only works for mono sounds, not for stereo sounds!
     * @param volume the volume in the range [0,1]
     * @param pitch  the pitch multiplier, 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
     * @param pan    panning in the range -1 (full left) to 1 (full right). 0 is center position.
     * @return the id of the sound instance if successful, or -1 on failure.
     */
    fun loop(volume: Float, pitch: Float, pan: Float): Long

    /**
     * Stops playing all instances of this sound.
     */
    fun stop()

    /**
     * Pauses all instances of this sound.
     */
    fun pause()

    /**
     * Resumes all paused instances of this sound.
     */
    fun resume()

    /**
     * Releases all the resources.
     */
    override fun dispose()

    /**
     * Stops the sound instance with the given id as returned by [.play()] or [.play(Float)]. If the sound is no
     * longer playing, this has no effect.
     * @param soundId the sound id.
     */
    fun stop(soundId: Long)

    /**
     * Pauses the sound instance with the given id as returned by [.play()] or [.play(Float)]. If the sound is no
     * longer playing, this has no effect.
     * @param soundId the sound id.
     */
    fun pause(soundId: Long)

    /**
     * Resumes the sound instance with the given id as returned by [.play()] or [.play(Float)]. If the sound is not
     * paused, this has no effect.
     * @param soundId the sound id
     */
    fun resume(soundId: Long)

    /**
     * Sets the sound instance with the given id to be looping. If the sound is no longer playing this has no effect.
     * @param soundId the sound id.
     * @param looping whether to loop or not.
     */
    fun setLooping(soundId: Long, looping: Boolean)

    /**
     * Changes the pitch multiplier of the sound instance with the given id as returned by [.play()] or
     * [.play(loat)]. If the sound is no longer playing, this has no effect.
     *
     * @param soundId the sound id
     * @param pitch   the pitch multiplier, 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
     */
    fun setPitch(soundId: Long, pitch: Float)

    /**
     * Changes the volume of the sound instance with the given id as returned by [.play()] or [.play(Float)]. If the
     * sound is no longer playing, this has no effect.
     * @param soundId the sound id
     * @param volume the volume in the range 0 (silent) to 1 (max volume).
     */
    fun setVolume(soundId: Long, volume: Float)

    /**
     * Sets the panning and volume of the sound instance with the given id as returned by [.play] or [.play].
     * If the sound is no longer playing, this has no effect. Note that panning only works for mono sounds, not for stereo sounds!
     *
     * @param soundId the sound id.
     * @param pan panning in the range -1 (full left) to 1 (full right). 0 is center position.
     * @param volume the volume in the range [0,1].
     */
    fun setPan(soundId: Long, pan: Float, volume: Float)
}
