package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.util.AudioPlayer;

public class BumpKillBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.BUMP_TILE) {
            Game.awardScore(collider.score, Location.entity(collider));
            level.getEntityManager().unloadEntity(entity);
            Game.particles.add(new FallingEnemyParticle(entity));
            AudioPlayer.KICK.play(Location.entity(entity));
        }
    }
}
