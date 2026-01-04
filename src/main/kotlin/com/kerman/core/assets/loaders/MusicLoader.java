package com.kerman.core.assets.loaders;

import com.kerman.core.Kerman;
import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.audio.Music;
import com.kerman.core.files.FileHandle;
import com.kerman.core.utils.KermanArray;

/**
 * This class was inspired by "com.badlogic.gdx.assets.loaders.MusicLoader".
 * <p>
 * {@link AssetLoader} for {@link Music} instances. The Music instance is loaded synchronously.
 */
public class MusicLoader extends AsynchronousAssetLoader<Music, MusicLoader.MusicParameter> {

    private Music music;

    public MusicLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Returns the {@link Music} instance currently loaded by this {@link MusicLoader}.
     *
     * @return the currently loaded {@link Music}, otherwise {@code null} if no {@link Music} has been loaded yet.
     */
    protected Music getLoadedMusic() {
        return music;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, MusicParameter parameter) {
        music = Kerman.audio.newMusic(file);
    }

    @Override
    public Music loadSync(AssetManager manager, String fileName, FileHandle file, MusicParameter parameter) {
        Music music = this.music;
        this.music = null;
        return music;
    }

    @Override
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, MusicParameter parameter) {
        return null;
    }

    static public class MusicParameter extends AssetLoaderParameters<Music> {
    }
}
