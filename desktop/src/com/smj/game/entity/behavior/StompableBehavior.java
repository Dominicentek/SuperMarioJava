package com.smj.game.entity.behavior;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.jmario.entity.physics.Physics;
import com.smj.util.AudioPlayer;

import java.awt.Point;

public class StompableBehavior implements EntityBehavior {
    private final EntityType spawn;
    public StompableBehavior(EntityType spawn) {
        this.spawn = spawn;
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) {
            if (Game.invincibilityTimeout > 0) {
                Game.awardScore(collider.score, Location.entity(collider));
                level.getEntityManager().unloadEntity(entity);
                Game.particles.add(new FallingEnemyParticle(entity));
                AudioPlayer.KICK.play(Location.entity(entity));
            }
            else {
                if (collider.getPhysics().getSpeedY() > (level.getPhysicsConfig().underwater ? level.getPhysicsConfig().underwaterGravity : level.getPhysicsConfig().gravity) * 1.5 || entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2 >= collider.getPhysics().getHitbox().y + collider.getPhysics().getHitbox().height) {
                    Game.awardScore(collider.score, Location.entity(collider));
                    level.getEntityManager().unloadEntity(entity);
                    Point center = new Point(entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2, entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height);
                    if (spawn != null) spawn.spawn(level, center.x - spawn.getHitbox().width / 2, center.y - spawn.getHitbox().height);
                    AudioPlayer.STOMP.play(Location.entity(entity));
                    Game.enemyKills++;
                    Main.actionQueue.push(() -> {
                        collider.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed);
                    });
                }
                else {
                    Game.damagePlayer();
                }
            }
        }
    }
}
