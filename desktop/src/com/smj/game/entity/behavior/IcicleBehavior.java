package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.SmokeParticle;
import com.smj.util.AudioPlayer;

import java.awt.Rectangle;

public class IcicleBehavior implements EntityBehavior {
    public boolean falling = false;
    public int prevY = 0;
    public int respawnTimeout = 0;
    public boolean grow = false;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
        entity.spawnOffsetX = 0;
        entity.spawnOffsetY = 100;
    }
    public void update(GameEntity entity, GameLevel level) {
        int x = (Game.player.getPhysics().getHitbox().x + Game.player.getPhysics().getHitbox().width / 2) - (entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2);
        if (respawnTimeout > 0) {
            respawnTimeout--;
            if (respawnTimeout == 0) {
                entity.getPhysics().getHitbox().y = prevY - 200;
                falling = false;
                grow = false;
            }
            return;
        }
        Rectangle playerHitbox = Game.player.getPhysics().getHitbox();
        Rectangle hitbox = entity.getPhysics().getHitbox();
        int px = playerHitbox.x + playerHitbox.width / 2;
        int py = playerHitbox.y + playerHitbox.height / 2;
        int X = hitbox.x + hitbox.width / 2;
        int Y = hitbox.y + hitbox.height / 2;
        if (!grow) grow = Math.sqrt(Math.pow(px - X, 2) + Math.pow(py - Y, 2)) > 400;
        if (!falling && entity.getPhysics().getHitbox().y < prevY) {
            if (grow) entity.getPhysics().getHitbox().y += 5;
            return;
        }
        entity.getProperties().setDrawInBG(false);
        entity.getPhysics().getConfig().collisionEnabled = true;
        if (entity.getPhysics().getHitbox().y > level.getLevelBoundaries().height * 100) reset(entity, level);
        if (x < -200 || x > 200 || playerHitbox.y < hitbox.y || falling || respawnTimeout > 0) return;
        prevY = entity.getPhysics().getHitbox().y;
        entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity;
        entity.getPhysics().setSpeedY(0);
        falling = true;
        AudioPlayer.ICICLE_FALL.play(Location.entity(entity));
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) {
            if (Game.invincibilityTimeout > 0) {
                AudioPlayer.ICICLE_BREAK.play(Location.entity(entity));
                reset(entity, level);
            }
            else Game.damagePlayer();
        }
    }
    public void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {
        if (!falling) return;
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        AudioPlayer.ICICLE_BREAK.play(Location.entity(entity));
        Rectangle hitbox = entity.getPhysics().getHitbox();
        Game.particles.add(new SmokeParticle((hitbox.x + hitbox.width / 2) * 16 / 100, (hitbox.y + hitbox.height) * 16 / 100));
        reset(entity, level);
    }
    public void reset(GameEntity entity, GameLevel level) {
        Rectangle hitbox = entity.getPhysics().getHitbox();
        hitbox.y = level.getLevelBoundaries().height * 100;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().setSpeedY(0);
        entity.getPhysics().getConfig().collisionEnabled = false;
        respawnTimeout = 60;
        entity.getProperties().setDrawInBG(true);
    }
}
