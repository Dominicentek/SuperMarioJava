package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.ShellBehavior;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class TimeTile extends GameTile {
    public int amount;
    public TimeTile(int amount) {
        this.amount = amount;
    }
    public void init(GameLevel level, int x, int y) {
        spotlight(x, y, 24, 160);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER && entity.getBehavior(ShellBehavior.class) == null) return;
        Game.time += amount;
        AudioPlayer.KEY_COIN.play(Location.entity(entity));
        Game.addTileReplacement(x, y, Tiles.AIR);
        Game.awardScore(StaticScore.COIN, Location.tile(x, y, level));
        level.setTileAt(Tiles.AIR, x, y);
    }
}
