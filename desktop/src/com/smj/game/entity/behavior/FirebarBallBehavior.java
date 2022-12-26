package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class FirebarBallBehavior implements EntityBehavior {
    public int originX;
    public int originY;
    public int distance;
    public int angle = 0;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        angle += 2;
        if (angle < 0) angle += 360;
        if (angle > 0) angle -= 360;
        entity.getPhysics().getHitbox().x = (int)(originX + (distance * 0.75) * Math.cos(Math.toRadians(angle)) - (distance * 0.75) * Math.sin(Math.toRadians(angle)));
        entity.getPhysics().getHitbox().y = (int)(originY + (distance * 0.75) * Math.sin(Math.toRadians(angle)) + (distance * 0.75) * Math.cos(Math.toRadians(angle)));
    }
}
