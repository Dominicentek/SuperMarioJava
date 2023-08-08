package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class CheckpointTile extends GameTile {
    private final Corner corner;
    public CheckpointTile(Corner corner) {
        this.corner = corner;
        if (corner == Corner.TOP_LEFT) spotlight(8, 8, 64, 255);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (Game.currentChallenge != null) return;
        int topLeftX = x + corner.toTopLeftX;
        int topLeftY = y + corner.toTopLeftY;
        Game.checkpointX = topLeftX;
        Game.checkpointY = topLeftY + 1;
        Game.checkpointTime = Game.time;
        level.setTileAt(Tiles.COLLECTED_CHECKPOINT_TOP_LEFT, topLeftX, topLeftY);
        level.setTileAt(Tiles.COLLECTED_CHECKPOINT_TOP_RIGHT, topLeftX + 1, topLeftY);
        level.setTileAt(Tiles.COLLECTED_CHECKPOINT_BOTTOM_LEFT, topLeftX, topLeftY + 1);
        level.setTileAt(Tiles.COLLECTED_CHECKPOINT_BOTTOM_RIGHT, topLeftX + 1, topLeftY + 1);
        Game.addTileReplacement(topLeftX, topLeftY, Tiles.COLLECTED_CHECKPOINT_TOP_LEFT);
        Game.addTileReplacement(topLeftX + 1, topLeftY, Tiles.COLLECTED_CHECKPOINT_TOP_RIGHT);
        Game.addTileReplacement(topLeftX, topLeftY + 1, Tiles.COLLECTED_CHECKPOINT_BOTTOM_LEFT);
        Game.addTileReplacement(topLeftX + 1, topLeftY + 1, Tiles.COLLECTED_CHECKPOINT_BOTTOM_RIGHT);
        AudioPlayer.CHECKPOINT.play(Location.entity(entity));
        Game.awardScore(StaticScore.CHECKPOINT);
    }
}
