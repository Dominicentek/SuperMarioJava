package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class FlyingBehavior implements EntityBehavior {
    public double speed;
    public FlyingBehavior(Double speed) {
        this.speed = speed;
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        entity.getPhysics().getHitbox().x -= speed;
    }
}
