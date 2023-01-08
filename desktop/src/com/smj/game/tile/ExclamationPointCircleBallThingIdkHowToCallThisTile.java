package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.util.AudioPlayer;

public class ExclamationPointCircleBallThingIdkHowToCallThisTile extends GameTile {
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType == EntityType.PLAYER) {
            EntityType.BRICK_BLOCK.spawn(level, level.getLevelBoundaries().width * 100 - 300, -1000);
            AudioPlayer.KEY_COIN.play(Location.tile(x, y, level));
            level.setTileAt(Tiles.AIR, x, y);
            Game.bossFightTilesTimeout = 1;
        }
    }
}
