package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;

public class StarTile extends GameTile {
    public void init(GameLevel level, int x, int y) {
        spotlight(x, y, 24, 255);
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (Game.currentChallenge != null && !Game.currentChallenge.allowStarFinish) return;
        level.setTileAt(Tiles.AIR, x, y);
        int scoreMultiplier = (int)Math.ceil(Game.invincibilityTimeout / 60f);
        if (scoreMultiplier >= 1 && Game.currentChallenge == null) Game.awardScore(StaticScore.STAR_FINISH.applyMultiplier(scoreMultiplier));
        Game.invincibilityTimeout = 0;
        Game.finishLevel();
    }
}
