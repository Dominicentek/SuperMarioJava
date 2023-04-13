package com.smj.game.entity.texture;

import com.smj.game.Game;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.BumpTileBehavior;
import com.smj.game.tile.GameTile;
import com.smj.jmario.tile.level.LevelTile;

import java.awt.Rectangle;

public class TileTextureProvider extends TextureProvider {
    public TileTextureProvider() {
        super(Game.THEMES[Game.currentLevel.theme].tileset);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        int tileID = entity.getBehavior(BumpTileBehavior.class).tile;
        LevelTile levelTile = entity.getLevel().getTileList().get(tileID);
        if (!(levelTile instanceof GameTile)) return new Rectangle(0, 0, 16, 16);
        GameTile tile = (GameTile)levelTile;
        int textureLocation = tile.getCurrentTextureLocation();
        int x = textureLocation % 16;
        int y = textureLocation / 16;
        return new Rectangle(x * 16, y * 16, 16, 16);
    }
}
