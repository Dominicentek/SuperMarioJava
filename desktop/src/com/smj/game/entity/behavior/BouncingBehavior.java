package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class BouncingBehavior implements EntityBehavior {
    public float factor;
    public BouncingBehavior(Float factor) {
        this.factor = factor;
    }
    public void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        entity.getPhysics().setSpeedY(-entity.getPhysics().getConfig().jumpingSpeed * factor);
    }
}
