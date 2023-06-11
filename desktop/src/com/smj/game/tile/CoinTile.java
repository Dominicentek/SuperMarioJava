package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class CoinTile extends GameTile {
    public void init(GameLevel level, int x, int y) {
        spotlight(x, y, 24, 160);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        level.setTileAt(Tiles.AIR, x, y);
        Game.addCoins(1);
        AudioPlayer.COIN.play();
        Game.awardScore(StaticScore.COIN, Location.tile(x, y, level));
    }
}
