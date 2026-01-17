package com.kerman.core.maps.tiled.objects;

import com.kerman.core.graphics.k2d.TextureRegion;
import com.kerman.core.maps.MapObject;
import com.kerman.core.maps.objects.TextureMapObject;
import com.kerman.core.maps.tiled.TiledMapTile;
import com.kerman.core.maps.tiled.tiles.AnimatedTiledMapTile;
import com.kerman.core.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Info : This class was inspired by "com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject".
 * <p>
 * A {@link MapObject} with a {@link TiledMapTile}. Can be both {@link StaticTiledMapTile} or {@link AnimatedTiledMapTile}. For
 * compatibility reasons, this extends {@link TextureMapObject}. Use {@link TiledMapTile#getTextureRegion()} instead of
 * {@link #getTextureRegion()}.
 */
public class TiledMapTileMapObject extends TextureMapObject {

    private boolean flipHorizontally;
    private boolean flipVertically;

    private TiledMapTile tile;

    public TiledMapTileMapObject(TiledMapTile tile, boolean flipHorizontally, boolean flipVertically) {
        this.flipHorizontally = flipHorizontally;
        this.flipVertically = flipVertically;
        this.tile = tile;

        TextureRegion textureRegion = new TextureRegion(tile.getTextureRegion());
        textureRegion.flip(flipHorizontally, flipVertically);
        setTextureRegion(textureRegion);
    }

    public boolean isFlipHorizontally() {
        return flipHorizontally;
    }

    public void setFlipHorizontally(boolean flipHorizontally) {
        this.flipHorizontally = flipHorizontally;
    }

    public boolean isFlipVertically() {
        return flipVertically;
    }

    public void setFlipVertically(boolean flipVertically) {
        this.flipVertically = flipVertically;
    }

    public TiledMapTile getTile() {
        return tile;
    }

    public void setTile(TiledMapTile tile) {
        this.tile = tile;
    }
}
