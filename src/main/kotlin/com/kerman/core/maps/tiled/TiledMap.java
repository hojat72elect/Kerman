package com.kerman.core.maps.tiled;

import com.kerman.core.assets.AssetManager;
import com.kerman.core.maps.Map;
import com.kerman.core.utils.Disposable;
import com.kerman.core.utils.KermanArray;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.TiledMap".
 * <p>
 * Represents a tiled map, adds the concept of tiles and tilesets.
 * @see Map
 */
public class TiledMap extends Map {
    private final TiledMapTileSets tilesets;
    private KermanArray<? extends Disposable> ownedResources;

    /**
     * Creates an empty TiledMap.
     */
    public TiledMap() {
        tilesets = new TiledMapTileSets();
    }

    /**
     * @return collection of tilesets for this map.
     */
    public TiledMapTileSets getTileSets() {
        return tilesets;
    }

    /**
     * Used by loaders to set resources when loading the map directly, without {@link AssetManager}. To be disposed in
     * {@link #dispose()}.
     */
    public void setOwnedResources(KermanArray<? extends Disposable> resources) {
        this.ownedResources = resources;
    }

    @Override
    public void dispose() {
        if (ownedResources != null) {
            for (Disposable resource : ownedResources) {
                resource.dispose();
            }
        }
    }
}
