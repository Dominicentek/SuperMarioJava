package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class CrateBehavior implements EntityBehavior {
    public void update(GameEntity entity, GameLevel level) {
        if (level.fluid == null) return;

        // i had to fight with the physics engine for this solution but it works so :p
        if (level.fluid.movement.getFluidLevel() * 100 / 16 < entity.getPhysics().getHitbox().y + 100) {
            entity.getPhysics().getConfig().gravity = 0;
            entity.getPhysics().getConfig().underwaterGravity = 0;
            entity.getPhysics().getHitbox().y = level.fluid.movement.getFluidLevel() * 100 / 16 - 50;
            int tileX = entity.getPhysics().getHitbox().x / 100;
            int tileY = entity.getPhysics().getHitbox().y / 100;
            while (level.getTileList().get(level.getTileAt(tileX, tileY)).isSolid()) {
                entity.getPhysics().getHitbox().y = (tileY + 1) * 100;
                tileX = entity.getPhysics().getHitbox().x / 100;
                tileY = entity.getPhysics().getHitbox().y / 100;
            }
        }
        else {
            entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity;
            entity.getPhysics().getConfig().underwaterGravity = level.getPhysicsConfig().underwaterGravity;
        }
    }
}
