package com.smj.game.entity.behavior;

import com.badlogic.gdx.math.Rectangle;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.ExplosionParticle;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.game.particle.IceExplosionParticle;
import com.smj.util.AudioPlayer;

public class IceballVulnerableBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.ICEBALL) {
            level.getEntityManager().unloadEntity(entity);
            level.getEntityManager().unloadEntity(collider);
            Game.particles.add(new IceExplosionParticle((int)((entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) / 100.0 * 16), (int)((entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2) / 100.0 * 16)));
            EntityType.ICE_CUBE.spawn(level, entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2 - EntityType.ICE_CUBE.getHitbox().width / 2, entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height - EntityType.ICE_CUBE.getHitbox().height);
            AudioPlayer.BUMP.play(Location.entity(entity));
            Game.enemyKills++;
        }
    }
}
