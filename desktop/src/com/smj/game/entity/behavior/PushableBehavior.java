package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.Entity;

import java.awt.Rectangle;

public class PushableBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        // these thigns are buggy as hell so dont you dare use them
        if (collider.entityType == EntityType.PLAYER || collider.entityType == EntityType.PUSHABLE_STONE) {
            Rectangle colliderHitbox = collider.getPhysics().getHitbox();
            Rectangle hitbox = entity.getPhysics().getHitbox();
            Rectangle leftCollision = new Rectangle(colliderHitbox.x, colliderHitbox.y, 1, colliderHitbox.height);
            Rectangle downCollision = new Rectangle(colliderHitbox.x, colliderHitbox.y + colliderHitbox.height + 1, colliderHitbox.width, 1);
            Rectangle upCollision = new Rectangle(colliderHitbox.x + 25, colliderHitbox.y - 1, colliderHitbox.width - 50, 1);
            if (hitbox.intersects(upCollision)) {
                if (collider.entityType == EntityType.PLAYER) Game.damagePlayer(true);
                return;
            }
            if (hitbox.intersects(downCollision)) return;
            hitbox.x = leftCollision.intersects(hitbox) ? colliderHitbox.x - hitbox.width : colliderHitbox.x + colliderHitbox.width;
            int x = entity.getPhysics().getHitbox().x / 100;
            int y = entity.getPhysics().getHitbox().x / 100;
            for (Entity e : level.getEntityManager()) {
                if (e == entity) continue;
                if (e.getPhysics().getHitbox().intersects(hitbox)) onEntityCollide((GameEntity)e, level, entity);
            }
            for (int X = x - 1; X <= x + 1; X++) {
                for (int Y = y - 1; Y <= y + 1; Y++) {
                    if (X < 0 || Y < 0 || X >= level.getLevelBoundaries().width || Y >= level.getLevelBoundaries().height) continue;
                    if (entity.getPhysics().collision[X][Y]) {
                        Rectangle tileHitbox = new Rectangle(X * 100, Y * 100, 100, 100);
                        if (hitbox.intersects(tileHitbox)) {
                            Rectangle leftHitbox = new Rectangle(hitbox.x, hitbox.y, 1, hitbox.height);
                            hitbox.x = leftHitbox.intersects(tileHitbox) ? tileHitbox.x + tileHitbox.width : tileHitbox.x - hitbox.width;
                        }
                    }
                }
            }
        }
    }
}
