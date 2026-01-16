package com.kerman.core.maps.tiled;

import com.kerman.core.maps.MapProperties;
import com.kerman.core.utils.IntMap;
import java.util.Iterator;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.TiledMapTileSet".
 * <p>
 * Set of {@link TiledMapTile} instances used to compose a TiledMapLayer.
 */
public class TiledMapTileSet implements Iterable<TiledMapTile> {

    private final IntMap<TiledMapTile> tiles;
    private final MapProperties properties;
    private String name;

    /**
     * Creates empty tileset
     */
    public TiledMapTileSet() {
        tiles = new IntMap<TiledMapTile>();
        properties = new MapProperties();
    }

    /**
     * @return tileset's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name for the tileset
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return tileset's properties set
     */
    public MapProperties getProperties() {
        return properties;
    }

    /**
     * Gets the {@link TiledMapTile} that has the given id.
     *
     * @param id the id of the {@link TiledMapTile} to retrieve.
     * @return tile matching id, null if it doesn't exist
     */
    public TiledMapTile getTile(int id) {
        return tiles.get(id);
    }

    /**
     * @return iterator to tiles in this tileset
     */
    @Override
    public Iterator<TiledMapTile> iterator() {
        return tiles.values().iterator();
    }

    /**
     * Adds or replaces tile with that id
     *
     * @param id   the id of the {@link TiledMapTile} to add or replace.
     * @param tile the {@link TiledMapTile} to add or replace.
     */
    public void putTile(int id, TiledMapTile tile) {
        tiles.put(id, tile);
    }

    /**
     * @param id tile's id to be removed
     */
    public void removeTile(int id) {
        tiles.remove(id);
    }

    /**
     * @return the size of this TiledMapTileSet.
     */
    public int size() {
        return tiles.size;
    }
}
