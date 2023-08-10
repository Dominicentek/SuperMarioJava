package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class BumpBounceBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.BUMP_TILE) entity.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed);
    }
}
