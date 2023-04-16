package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class WarperBehavior implements EntityBehavior {
    public static int warperTimeout = 0;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER && warperTimeout == 0) {
            Game.currentLevel.camera.targetX -= 48;
            Game.currentLevel.camera.x -= 48;
            collider.getPhysics().getHitbox().x -= 4800;
            warperTimeout = 60;
        }
    }
}
