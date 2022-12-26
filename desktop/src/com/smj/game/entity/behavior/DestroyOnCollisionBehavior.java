package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.ExplosionParticle;
import com.smj.game.particle.IceExplosionParticle;
import com.smj.util.AudioPlayer;

public class DestroyOnCollisionBehavior implements EntityBehavior {
    public void onTileTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        touch(entity, level, x, y);
    }
    public void onTileTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        touch(entity, level, x, y);
    }
    public void onTileTouchUp(GameEntity entity, GameLevel level, int x, int y) {
        touch(entity, level, x, y);
    }
    public void touch(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        level.getEntityManager().unloadEntity(entity);
        int X = (int)((entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) / 100.0 * 16);
        int Y = (int)((entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2) / 100.0 * 16);
        if (entity.entityType == EntityType.FIREBALL) Game.particles.add(new ExplosionParticle(X, Y));
        if (entity.entityType == EntityType.ICEBALL) Game.particles.add(new IceExplosionParticle(X, Y));
        AudioPlayer.BUMP.play(Location.entity(entity));
    }
}
