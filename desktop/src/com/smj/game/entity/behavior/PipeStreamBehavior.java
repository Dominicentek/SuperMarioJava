package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.BubbleParticle;
import com.smj.util.RNG;

import java.awt.Rectangle;

public class PipeStreamBehavior implements EntityBehavior {
    public int dirX;
    public int dirY;
    public PipeStreamBehavior(Integer dirX, Integer dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.getPhysics().getConfig().collisionEnabled = false;
    }
    public void update(GameEntity entity, GameLevel level) {
        Rectangle hitbox = entity.getPhysics().getHitbox();
        int x = hitbox.x + hitbox.width / 2;
        int y = hitbox.y + hitbox.height / 2;
        x += hitbox.width / 2 * -dirX;
        y += hitbox.height / 2 * -dirY;
        int px = x * 16 / 100;
        int py = y * 16 / 100;
        int x1 = dirY * -16;
        int x2 = dirY * +16;
        int y1 = dirX * -16;
        int y2 = dirX * +16;
        Game.particles.add(new BubbleParticle(px + RNG.range(Math.min(x1, x2), Math.max(x1, x2)), py + RNG.range(Math.min(y1, y2), Math.max(y1, y2)), dirX * 0.5, dirY * 0.5));
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) {
            if (dirX != 0) collider.getPhysics().setSpeedX(dirX * 0.5);
            if (dirY != 0) collider.getPhysics().setSpeedY(dirY * 0.5);
        }
    }
}
