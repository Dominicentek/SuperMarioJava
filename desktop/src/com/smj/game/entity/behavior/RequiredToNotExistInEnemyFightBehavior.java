package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class RequiredToNotExistInEnemyFightBehavior implements EntityBehavior {
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.requiredToNotExistInEnemyFight = true;
    }
}
