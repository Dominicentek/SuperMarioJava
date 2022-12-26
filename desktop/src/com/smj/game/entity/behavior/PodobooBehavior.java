package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class PodobooBehavior implements EntityBehavior {
    public int jumpTimeout = 0;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (jumpTimeout > 0) {
            entity.getPhysics().setSpeedY(0);
            entity.getPhysics().getHitbox().y = level.getLevelBoundaries().height * 100;
            jumpTimeout--;
            if (jumpTimeout == 0) {
                entity.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed * 2.5);
                entity.getPhysics().getHitbox().y -= 25;
            }
        }
        if (jumpTimeout == 0) {
            if (entity.getPhysics().getHitbox().y >= level.getLevelBoundaries().height * 100) {
                jumpTimeout = 90;
            }
        }
    }
}
