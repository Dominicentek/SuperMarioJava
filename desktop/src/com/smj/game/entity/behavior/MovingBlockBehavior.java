package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class MovingBlockBehavior implements EntityBehavior {
    public double speed;
    public float factor = 1;
    public MovingBlockBehavior(Double speed) {
        this.speed = speed;
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().collideWithOwnType = false;
        entity.getPhysics().fixedSpeedX = true;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        entity.getPhysics().setSpeedX(speed * factor);
    }
    public void onTileTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        factor = 1;
    }
    public void onTileTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        factor = -1;
    }
}
