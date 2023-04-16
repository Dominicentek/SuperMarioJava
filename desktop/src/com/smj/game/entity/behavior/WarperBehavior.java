package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class WarperBehavior implements EntityBehavior {
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) {
            Game.currentLevel.camera.targetX -= 768;
            Game.currentLevel.camera.x -= 768;
            collider.getPhysics().getHitbox().x -= 3200;
        }
    }
}
