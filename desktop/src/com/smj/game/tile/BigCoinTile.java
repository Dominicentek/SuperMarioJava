package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.ShellBehavior;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class BigCoinTile extends GameTile {
    private int amount;
    private int score;
    private Corner corner;
    public BigCoinTile(int amount, int score, Corner corner) {
        this.amount = amount;
        this.score = score;
        this.corner = corner;
        if (corner == Corner.TOP_LEFT) spotlight(8, 8, 48, 192);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER && entity.getBehavior(ShellBehavior.class) == null) return;
        AudioPlayer.BIG_COIN.play();
        int topLeftX = corner.toTopLeftX + x;
        int topLeftY = corner.toTopLeftY + y;
        Game.awardScore(new StaticScore().score(score).coins(amount), Location.custom((topLeftX + 1) * 16, (topLeftY + 1) * 16, 32, 32, level));
        Game.spawnSparklesTile(topLeftX, topLeftY, 5);
        Game.spawnSparklesTile(topLeftX + 1, topLeftY, 5);
        Game.spawnSparklesTile(topLeftX, topLeftY + 1, 5);
        Game.spawnSparklesTile(topLeftX + 1, topLeftY + 1, 5);
        level.setTileAt(Tiles.AIR, topLeftX, topLeftY);
        level.setTileAt(Tiles.AIR, topLeftX + 1, topLeftY);
        level.setTileAt(Tiles.AIR, topLeftX, topLeftY + 1);
        level.setTileAt(Tiles.AIR, topLeftX + 1, topLeftY + 1);
        Game.addTileReplacement(topLeftX, topLeftY, Tiles.AIR);
        Game.addTileReplacement(topLeftX + 1, topLeftY, Tiles.AIR);
        Game.addTileReplacement(topLeftX, topLeftY + 1, Tiles.AIR);
        Game.addTileReplacement(topLeftX + 1, topLeftY + 1, Tiles.AIR);
    }
}
