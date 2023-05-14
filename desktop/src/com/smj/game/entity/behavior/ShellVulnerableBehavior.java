package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.util.AudioPlayer;

public class ShellVulnerableBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (isShell(collider.entityType)) {
            if (collider.getBehavior(ShellBehavior.class).speedFactor != 0) {
                Game.awardScore(collider.score, Location.entity(collider));
                level.getEntityManager().unloadEntity(entity);
                Game.particles.add(new FallingEnemyParticle(entity));
                AudioPlayer.KICK.play(Location.entity(entity));
            }
            if (isShell(entity.entityType)) {
                Game.awardScore(entity.score, Location.entity(entity));
                level.getEntityManager().unloadEntity(collider);
                Game.particles.add(new FallingEnemyParticle(collider));
                AudioPlayer.KICK.play(Location.entity(collider));
            }
        }
    }
    public boolean isShell(EntityType type) {
        return type == EntityType.GREEN_KOOPA_SHELL || type == EntityType.RED_KOOPA_SHELL || type == EntityType.BUZZY_BEETLE_SHELL;
    }
}
