package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.ExplosionParticle;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.game.particle.IceExplosionParticle;
import com.smj.util.AudioPlayer;

public class FireballMeltingBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.FIREBALL) {
            level.getEntityManager().unloadEntity(entity);
            level.getEntityManager().unloadEntity(collider);
            Game.particles.add(new ExplosionParticle((int)((collider.getPhysics().getHitbox().x + collider.getPhysics().getHitbox().width / 2) / 100.0 * 16), (int)((collider.getPhysics().getHitbox().y + collider.getPhysics().getHitbox().height / 2) / 100.0 * 16)));
            Game.particles.add(new IceExplosionParticle((int)((entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) / 100.0 * 16), (int)((entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2) / 100.0 * 16)));
            AudioPlayer.BUMP.play(Location.entity(entity));
        }
    }
}
