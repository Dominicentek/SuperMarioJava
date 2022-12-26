package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class BulletBehavior implements EntityBehavior {
    public int age = 0;
    public void update(GameEntity entity, GameLevel level) {
        age++;
        if (age == 15) entity.getProperties().drawInBG = false;
    }
}
