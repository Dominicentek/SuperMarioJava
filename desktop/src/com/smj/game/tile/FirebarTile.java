package com.smj.game.tile;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.FirebarBallBehavior;

public class FirebarTile extends GameTile {
    public void init(GameLevel level, int x, int y) {
        for (int i = 0; i < 7; i++) {
            GameEntity fireball = EntityType.FIREBAR_FIREBALL.spawn(level, x * 100 + 25, y * 100 + 25);
            FirebarBallBehavior behavior = fireball.getBehavior(FirebarBallBehavior.class);
            behavior.distance = i * 50;
            behavior.originX = x * 100 + 25;
            behavior.originY = y * 100 + 25;
        }
    }
}
