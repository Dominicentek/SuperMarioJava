package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class WindBehavior implements EntityBehavior {
    public void update(GameEntity entity, GameLevel level) {
        if (level.gimmick == GameLevel.Gimmick.WIND) {
            entity.getPhysics().windSpeed = level.getPhysicsConfig().maxWalkingSpeed * 2 / 3;
        }
    }
}
