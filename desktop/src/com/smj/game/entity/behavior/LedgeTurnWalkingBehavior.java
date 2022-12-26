package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

public class LedgeTurnWalkingBehavior extends WalkingBehavior {
    public LedgeTurnWalkingBehavior(Double speed) {
        super(speed);
    }
    public void update(GameEntity entity, GameLevel level) {
        super.update(entity, level);
        Rectangle hitbox = entity.getPhysics().getHitbox();
        Point downPoint = new Point(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + 20);
        int x = downPoint.x / 100;
        int y = downPoint.y / 100;
        if (x < 0 || y < 0 || x >= entity.getLevel().getLevelBoundaries().width || y >= entity.getLevel().getLevelBoundaries().height) return;
        if (!entity.getPhysics().collision[x][y] && !entity.getPhysics().isInAir()) {
            speedFactor *= -1;
            if (speedFactor > 0) entity.getPhysics().getHitbox().x += 10;
            if (speedFactor < 0) entity.getPhysics().getHitbox().x -= 10;
        }
    }
}
