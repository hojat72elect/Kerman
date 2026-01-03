package com.kerman.core.audio

import com.kerman.core.utils.Disposable
import com.kerman.core.Audio
import com.kerman.core.Application
import com.kerman.core.ApplicationListener

/**
 * Info : This interface was inspired by "com.badlogic.gdx.audio.Music".
 *
 * A Music instance represents a streamed audio file. The interface supports pausing, resuming and so on. When you are done with
 * using the Music instance you have to dispose it via the [.dispose] method.
 *
 * Music instances are created via [Audio.newMusic].
 * Music instances are automatically paused and resumed when an [Application] is paused or resumed. See
 * [ApplicationListener].
 * **Note**: any values provided will not be clamped, it is the developer's responsibility to do so.
 */
interface Music : Disposable {

    /**
     * Starts the play back of the music stream. In case the stream was paused this will resume the play back. In case the music
     * stream is finished playing this will restart the play back.
     */
    fun play()

    /**
     * Pauses the play back. If the music stream has not been started yet or has finished playing a call to this method will be
     * ignored.
     */
    fun pause()

    /**
     * Stops a playing or paused Music instance. Next time play() is invoked the Music will start from the beginning.
     */
    fun stop()

    /**
     * @return whether this music stream is playing.
     */
    fun isPlaying(): Boolean

    /**
     * @return whether the music stream is playing.
     */
    fun isLooping(): Boolean

    /**
     * Sets whether the music stream is looping. This can be called at any time, whether the stream is playing.
     * @param isLooping whether to loop the stream or not.
     */
    fun setLooping(isLooping: Boolean)

    /**
     * @return the volume of this music stream.
     */
    fun getVolume(): Float

    /**
     * Sets the volume of this music stream. The volume must be given in the range [0,1] with 0 being silent and 1 being the
     * maximum volume.
     */
    fun setVolume(volume: Float)

    /**
     * Sets the panning and volume of this music stream.
     *
     * @param pan panning in the range -1 (full left) to 1 (full right). 0 is center position.
     * @param volume the volume in the range [0,1].
     */
    fun setPan(pan: Float, volume: Float)

    /**
     * Returns the playback position in seconds.
     */
    fun getPosition(): Float

    /**
     * Set the playback position in seconds.
     */
    fun setPosition(position: Float)

    /**
     * Needs to be called when the Music is no longer needed.
     */
    override fun dispose()

    /**
     * Register a callback to be invoked when the end of a music stream has been reached during playback.
     * @param listener the callback that will be run.
     */
    fun setOnCompletionListener(listener: OnCompletionListener)

    /**
     * Interface definition for a callback to be invoked when playback of a music stream has completed.
     */
    interface OnCompletionListener {
        /**
         * Called when the end of a media source is reached during playback.
         * @param music the Music that reached the end of the file
         */
        fun onCompletion(music: Music)
    }
}
