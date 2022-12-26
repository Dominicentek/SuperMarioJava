package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class EmergeBehavior implements EntityBehavior {
    public int emergeTimeout = 50;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.getProperties().drawInBG = true;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (emergeTimeout > 0) {
            emergeTimeout--;
            entity.getPhysics().getHitbox().y -= 2;
            if (emergeTimeout == 0) {
                entity.getPhysics().getConfig().collisionEnabled = true;
                entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity;
                entity.getPhysics().getConfig().underwaterGravity = level.getPhysicsConfig().underwaterGravity;
                entity.getProperties().drawInBG = false;
            }
        }
    }
}
