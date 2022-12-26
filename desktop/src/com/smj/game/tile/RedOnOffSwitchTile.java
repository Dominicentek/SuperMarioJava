package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.util.AudioPlayer;

import java.awt.Dimension;

public class RedOnOffSwitchTile extends GameTile {
    public void onTouchBottom(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        Dimension bounds = level.getLevelBoundaries();
        AudioPlayer.SWITCH.play(Location.tile(x, y, level));
        Game.onOff = false;
        for (int X = 0; X < bounds.width; X++) {
            for (int Y = 0; Y < bounds.height; Y++) {
                int tile = level.getTileAt(X, Y);
                if (tile == Tiles.ON_OFF_SWITCH_RED) level.setTileAt(Tiles.ON_OFF_SWITCH_BLUE, X, Y);
                if (tile == Tiles.ON_OFF_BLOCK_RED_ON) level.setTileAt(Tiles.ON_OFF_BLOCK_RED_OFF, X, Y);
                if (tile == Tiles.ON_OFF_BLOCK_BLUE_OFF) level.setTileAt(Tiles.ON_OFF_BLOCK_BLUE_ON, X, Y);
            }
        }
    }
}
