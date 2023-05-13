package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class CoinDeathRewardBehavior implements EntityBehavior {
    public void onUnload(GameEntity entity, GameLevel level) {
        if (entity.getPhysics().getHitbox().y >= level.getLevelBoundaries().height * 100) return;
        int x = entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2;
        int y = entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2;
        x -= EntityType.COIN.getHitbox().width / 2;
        y -= EntityType.COIN.getHitbox().width / 2;
        EntityType.COIN.spawn(level, x, y - 100);
    }
}
