package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.ShellBehavior;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

import java.awt.Point;
import java.util.ArrayList;

public class KeyCoinTile extends GameTile {
    public void init(GameLevel level, int x, int y) {
        spotlight(x, y, 24, 160);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER && entity.getBehavior(ShellBehavior.class) == null) return;
        level.setTileAt(Tiles.AIR, x, y);
        Game.addCoins(1);
        Game.keycoinsCollected++;
        Game.awardScore(StaticScore.COIN, Location.tile(x, y, level));
        boolean moreKeyCoins = false;
        ArrayList<Point> starLocations = new ArrayList<>();
        for (int X = 0; X < level.getLevelBoundaries().width; X++) {
            for (int Y = 0; Y < level.getLevelBoundaries().height; Y++) {
                if (level.getTileAt(X, Y) == Tiles.LOCKED_STAR) starLocations.add(new Point(X, Y));
                if (level.getTileAt(X, Y) == Tiles.KEY_COIN) moreKeyCoins = true;
            }
        }
        if (!moreKeyCoins) {
            AudioPlayer.ALL_KEY_COINS.play();
            for (Point point : starLocations) {
                level.setTileAt(Tiles.STAR, point.x, point.y);
            }
        }
        else AudioPlayer.KEY_COIN.play();
    }
}
