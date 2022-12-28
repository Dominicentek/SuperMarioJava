package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;
import com.smj.util.RNG;

import java.util.Random;

public class BrickBehavior implements EntityBehavior {
    public int timeout = 300;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().fixedSpeedX = true;
        entity.getPhysics().setSpeedY((RNG.range(0.5f, 0.75f)) * -entity.getPhysics().getConfig().jumpingSpeed);
        entity.getPhysics().setSpeedX((RNG.chance(1 / 2f) ? -1 : 1) * (RNG.range(0.05f, 0.15f)));
    }
    public void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {
        if (level.getTileList().get(level.getTileAt(x, y)).isSolid()) entity.getPhysics().setSpeedX(0);
    }
    public void update(GameEntity entity, GameLevel level) {
        timeout--;
        if (timeout == 0) level.getEntityManager().unloadEntity(entity);
    }
}
