package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.game.score.StaticScore;
import com.smj.jmario.entity.physics.Physics;
import com.smj.util.AudioPlayer;

public class ShellBehavior extends WalkingBehavior {
    public int cooldown = 10;
    public ShellBehavior() {
        super(1.0986328125);
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        speedFactor = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        super.update(entity, level);
        if (cooldown > 0) cooldown--;
    }
    public void onTileTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        speedFactor = 0.5f;
        AudioPlayer.BUMP.play(Location.entity(entity));
    }
    public void onTileTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        speedFactor = -1;
        AudioPlayer.BUMP.play(Location.entity(entity));
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
                if (speedFactor == 0) {
                    entity.score.reset();
                    int colliderHitboxCenter = collider.getPhysics().getHitbox().x + collider.getPhysics().getHitbox().width / 2;
                    int hitboxCenter = entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2;
                    if (colliderHitboxCenter > hitboxCenter) speedFactor = -1;
                    else speedFactor = 0.5f;
                    cooldown = 10;
                    AudioPlayer.KICK.play(Location.entity(entity));
                    Game.awardScore(StaticScore.SHELL_KICK);
                }
                else if (collider.getPhysics().getSpeedY() > (level.getPhysicsConfig().underwater ? level.getPhysicsConfig().underwaterGravity : level.getPhysicsConfig().gravity) * 1.5) {
                    speedFactor = 0;
                    cooldown = 10;
                    Physics physics = collider.getPhysics();
                    physics.setSpeedY(-level.getPhysicsConfig().jumpingSpeed);
                    Game.awardScore(collider.score, Location.entity(collider));
                }
                else if (cooldown == 0) Game.damagePlayer();
            }
        }
    }
    public void changeDirection() {
        speedFactor *= -1;
        if (speedFactor == 1) speedFactor = 0.5f;
        if (speedFactor == -0.5f) speedFactor = -1;
    }
}
