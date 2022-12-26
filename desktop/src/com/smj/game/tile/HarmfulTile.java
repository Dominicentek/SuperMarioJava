package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class HarmfulTile extends GameTile {
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType == EntityType.PLAYER && Game.invincibilityTimeout == 0) Game.damagePlayer();
    }
}
