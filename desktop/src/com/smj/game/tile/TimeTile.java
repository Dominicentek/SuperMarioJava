package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class TimeTile extends GameTile {
    public int amount;
    public TimeTile(int amount) {
        this.amount = amount;
    }
    private GameEntity spotlight;
    public void init(GameLevel level, int x, int y) {
        spotlight = spotlight(level, x, y, 24, 160);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        level.getEntityManager().unloadEntity(spotlight);
        Game.time += amount;
        AudioPlayer.KEY_COIN.play(Location.entity(entity));
        Game.awardScore(StaticScore.COIN, Location.tile(x, y, level));
        level.setTileAt(Tiles.AIR, x, y);
    }
}
