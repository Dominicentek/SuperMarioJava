package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class BumpTileBehavior implements EntityBehavior {
    private int y;
    public int tile = 0;
    public Runnable after;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed / 3);
        y = entity.getPhysics().getHitbox().y;
        level.setTileAt(0, entity.getPhysics().getHitbox().x / 100, entity.getPhysics().getHitbox().y / 100);
        entity.getPhysics().getConfig().solidHitbox = true;
        entity.getPhysics().getConfig().collisionEnabled = false;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (entity.getPhysics().getHitbox().y <= y) return;
        level.getEntityManager().unloadEntity(entity);
        level.setTileAt(tile, entity.getPhysics().getHitbox().x / 100, entity.getPhysics().getHitbox().y / 100);
        after.run();
    }
}
