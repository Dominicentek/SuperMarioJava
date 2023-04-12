package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class CrateBehavior implements EntityBehavior {
    public void update(GameEntity entity, GameLevel level) {
        if (level.fluid == null) return;

        // the physics engine doesnt like this solution but fuck it
        if (level.fluid.movement.getFluidLevel() * 100 / 16 < entity.getPhysics().getHitbox().y + 100) {
            entity.getPhysics().getConfig().gravity = 0;
            entity.getPhysics().getConfig().underwaterGravity = 0;
            entity.getPhysics().getHitbox().y = level.fluid.movement.getFluidLevel() * 100 / 16 - 50;
        }
        else {
            entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity;
            entity.getPhysics().getConfig().underwaterGravity = level.getPhysicsConfig().underwaterGravity;
        }
    }
}
