package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class DespawnBehavior implements EntityBehavior {
    private int timeout;
    public DespawnBehavior(Integer timeout) {
        this.timeout = timeout;
    }
    public void update(GameEntity entity, GameLevel level) {
        timeout--;
        if (timeout == 0) level.getEntityManager().unloadEntity(entity);
    }
}
