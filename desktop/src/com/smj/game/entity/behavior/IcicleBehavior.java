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
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        int x = (Game.player.getPhysics().getHitbox().x + Game.player.getPhysics().getHitbox().width / 2) - (entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2);
        if (respawnTimeout > 0) {
            respawnTimeout--;
            if (respawnTimeout == 0) {
                entity.getPhysics().getHitbox().y = prevY - 200;
                falling = false;
            }
        }
        if (!falling && entity.getPhysics().getHitbox().y < prevY) {
            entity.getPhysics().getHitbox().y += 5;
            return;
        }
        entity.getProperties().setDrawInBG(false);
        if (x < -200 || x > 200 || Game.player.getPhysics().getHitbox().y < entity.getPhysics().getHitbox().y || falling || respawnTimeout > 0) return;
        prevY = entity.getPhysics().getHitbox().y;
        entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity;
        entity.getPhysics().setSpeedY(0);
        falling = true;
        AudioPlayer.ICICLE_FALL.play(Location.entity(entity));
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) Game.damagePlayer();
    }
    public void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        AudioPlayer.ICICLE_BREAK.play(Location.entity(entity));
        Rectangle hitbox = entity.getPhysics().getHitbox();
        Game.particles.add(new SmokeParticle((hitbox.x + hitbox.width / 2) * 16 / 100, (hitbox.y + hitbox.height) * 16 / 100));
        hitbox.y = level.getLevelBoundaries().height * 100;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().setSpeedY(0);
        respawnTimeout = 60;
        entity.getProperties().setDrawInBG(true);
    }
}
