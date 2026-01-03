package com.kerman.core

import com.kerman.core.audio.AudioDevice
import com.kerman.core.audio.AudioRecorder
import com.kerman.core.audio.Music
import com.kerman.core.audio.Sound
import com.kerman.core.files.FileHandle
import com.kerman.core.utils.Disposable
import com.kerman.core.utils.KermanRuntimeException

/**
 * Info : This interface was inspired by "com.badlogic.gdx.Audio".
 *
 * This interface encapsulates the creation and management of audio resources. It allows you to get direct access to the audio
 * hardware via the [AudioDevice] and [AudioRecorder] interfaces, create sound effects via the [Sound] interface
 * and play music streams via the [Music] interface.
 *
 * All resources created via this interface have to be disposed as soon as they are no longer used.
 *
 * Note that all [Music] instances will be automatically paused when the [ApplicationListener.pause] method is
 * called, and automatically resumed when the [ApplicationListener.resume] method is called.
 */
interface Audio : Disposable {

    /**
     * Creates a new [AudioDevice] either in mono or stereo mode. The AudioDevice has to be disposed via its
     * [AudioDevice.dispose] method when it is no longer used.
     *
     * @param samplingRate the sampling rate.
     * @param isMono       whether the AudioDevice should be in mono or stereo mode
     * @return the AudioDevice, or null (in case AudioDevice couldn't be created).
     * @throws KermanRuntimeException in case the device could not be created.
     */
    fun newAudioDevice(samplingRate: Int, isMono: Boolean): AudioDevice?

    /**
     * Creates a new [AudioRecorder]. The AudioRecorder has to be disposed after it is no longer used.
     *
     * @param samplingRate the sampling rate in Hertz
     * @param isMono       whether the recorder records in mono or stereo
     * @return the AudioRecorder, or null (In case the AudioRecorder could not be created).
     * @throws KermanRuntimeException in case the recorder could not be created
     */
    fun newAudioRecorder(samplingRate: Int, isMono: Boolean): AudioRecorder?

    /**
     * Creates a new [Sound] which is used to play back audio effects such as gun shots or explosions. The Sound's audio data
     * is retrieved from the file specified via the [FileHandle]. Note that the complete audio data is loaded into RAM. You
     * should therefore not load big audio files with this method. The current upper limit for decoded audio is 1 MB.
     *
     * Currently supported formats are WAV, MP3 and OGG.
     * The Sound has to be disposed if it is no longer used via the [Sound.dispose] method.
     *
     * @return the new Sound, or null (in case it could not be loaded).
     * @throws KermanRuntimeException in case the sound could not be loaded
     */
    fun newSound(fileHandle: FileHandle): Sound?

    /**
     * Creates a new [Music] instance which is used to play back a music stream from a file. Currently supported formats are
     * WAV, MP3 and OGG. The Music instance has to be disposed if it is no longer used via the [Music.dispose] method.
     * Music instances are automatically paused when [ApplicationListener.pause] is called and resumed when
     * [ApplicationListener.resume] is called.
     *
     * @param file the FileHandle
     * @return the new Music or null if the Music could not be loaded
     * @throws KermanRuntimeException in case the music could not be loaded
     */
    fun newMusic(file: FileHandle): Music?

    /**
     * Sets a new OutputDevice. The identifier can be retrieved from [Audio.getAvailableOutputDevices]. If null is passed,
     * it will switch to auto.
     * @param deviceIdentifier device identifier to switch to, or null for auto.
     */
    fun switchOutputDevice(deviceIdentifier: String?): Boolean

    /**
     * This function returns a list of fully qualified Output device names. This function is only implemented on desktop.
     * It will return an empty array on error.
     * The names returned need os dependent pre-processing before being exposed to the user.
     * @return An array of available output devices.
     */
    fun getAvailableOutputDevices(): Array<String>
}
