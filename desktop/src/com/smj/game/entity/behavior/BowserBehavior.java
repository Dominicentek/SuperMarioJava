package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FallingEnemyParticle;
import com.smj.game.score.BowserScore;
import com.smj.util.AudioPlayer;
import com.smj.util.RNG;
import org.w3c.dom.Entity;

import java.util.Random;

public class BowserBehavior implements EntityBehavior {
    private BowserScore score = new BowserScore();
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (RNG.chance(1 / 125f) && Game.bossFightBegan) EntityType.BOWSER_FIRE.spawn(level, entity.getPhysics().getHitbox().x - 100, entity.getPhysics().getHitbox().y + 50);
        entity.getPhysics().getHitbox().y = (int)(50 + (Math.sin(Math.toRadians(System.currentTimeMillis() / 20)) / 2 + 0.5) * 950);
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType != EntityType.BRICK_BLOCK) return;
        Game.awardScore(score, Location.entity(entity));
        Game.currentLevel.getEntityManager().unloadEntity(collider);
        if (score.step == 3) {
            Game.currentLevel.getEntityManager().unloadEntity(entity);
            Game.particles.add(new FallingEnemyParticle(entity));
            AudioPlayer.ALL_KEY_COINS.play();

        }
    }
}
