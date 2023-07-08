package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class WalkingBehavior implements EntityBehavior {
    public final double speed;
    public float speedFactor = -1;
    public WalkingBehavior(Double speed) {
        this.speed = speed;
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().fixedSpeedX = true;
    }
    public void onTileTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        speedFactor = 1;
    }
    public void onTileTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        speedFactor = -1;
    }
    public void update(GameEntity entity, GameLevel level) {
        entity.getPhysics().setSpeedX(speed * speedFactor);
    }
}
