package com.smj.jmario.entity.physics;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.PushableBehavior;
import com.smj.game.tile.Tiles;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.level.Level;
import com.smj.jmario.tile.level.LevelTile;
import com.smj.util.AudioPlayer;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public final class Physics {
    public boolean fixedSpeedX = false;
    private Entity entity;
    private PlayerMovement movement = new PlayerMovement();
    private PhysicsConfig config;
    private Rectangle hitbox;
    private Level level;
    private boolean begun;
    private double speedX = 0;
    private double speedY = 0;
    public double windSpeed;
    private Double nextSpeedX = null;
    private Double nextSpeedY = null;
    public boolean[][] collision;
    public boolean inAir = false;
    public boolean jumping = false;
    public boolean jumpingUpward = false;
    private double jumpOrigin = 0;
    private double accelerationFactor;
    public Physics(Entity entity, PhysicsConfig config) {
        hitbox = new Rectangle(0, 0, config.hitbox.width, config.hitbox.height);
        this.entity = entity;
        this.config = config;
    }
    public Physics begin(Level level) {
        this.level = level;
        begun = true;
        collision = new boolean[level.getLevelBoundaries().width][level.getLevelBoundaries().height];
        updateCollisionMap();
        return this;
    }
    public Physics advance() {
        if (!begun) throw new IllegalStateException("Simulation has not begun yet");
        long delta = 16;
        inAir = true;
        double maxSpeed = config.underwater ? (inAir ? config.maxUnderwaterSpeed : config.maxUnderwaterOnGroundSpeed) : (movement.isRunning() ? config.maxRunningSpeed : config.maxWalkingSpeed);
        if (config.collisionEnabled) {
            hitbox.y += speedY * delta;
            int tileX = (hitbox.x + hitbox.width / 2) / 100;
            int tileY = (hitbox.y + hitbox.height / 2) / 100;
            for (int x = tileX - hitbox.width / 200 - 1; x <= tileX + hitbox.width / 200 + 1; x++) {
                for (int y = tileY - hitbox.height / 200 - 1; y <= tileY + hitbox.height / 200 + 1; y++) {
                    if (x < 0 || y < 0 || x >= collision.length || y >= collision[0].length) continue;
                    Rectangle tileHitbox = new Rectangle(x * 100, y * 100, 100, 100);
                    if (tileHitbox.intersects(hitbox)) {
                        LevelTile tile = level.getTileList().get(level.getTileAt(x, y));
                        Rectangle upCollision = new Rectangle(hitbox.x, hitbox.y, hitbox.width, 1);
                        tile.onTouch(entity, level, x, y);
                        if (upCollision.intersects(tileHitbox)) {
                            tile.onTouchBottom(entity, level, x, y);
                            entity.onTileTouchUp(level, x, y);
                        }
                        else {
                            tile.onTouchTop(entity, level, x, y);
                            entity.onTileTouchDown(level, x, y);
                        }
                    }
                }
            }
            for (Entity entity : new ArrayList<>(Arrays.asList(level.getEntityManager().array()))) {
                if (this.entity == entity || !entity.getPhysics().getConfig().solidHitbox) continue;
                Rectangle entityHitbox = entity.getPhysics().hitbox;
                if (hitbox.intersects(entityHitbox)) {
                    Rectangle upCollision = new Rectangle(hitbox.x, hitbox.y, hitbox.width, 1);
                    speedY = 0;
                    int x = (entityHitbox.x + entityHitbox.width / 2) / 100;
                    int y = (entityHitbox.y + entityHitbox.height / 2) / 100;
                    entity.onEntityCollide(level, this.entity);
                    this.entity.onEntityCollide(level, entity);
                    int prevTile = level.getTileAt(x, y);
                    level.setTileAt(Tiles.BARRIER, x, y);
                    if (upCollision.intersects(entityHitbox)) {
                        hitbox.y = entityHitbox.y + entityHitbox.height;
                        jumpingUpward = false;
                        this.entity.onTileTouchUp(level, x, y);
                    }
                    else {
                        hitbox.y = entityHitbox.y - hitbox.height;
                        inAir = false;
                        this.entity.onTileTouchDown(level, x, y);
                    }
                    level.setTileAt(prevTile, x, y);
                }
            }
            for (int x = tileX - hitbox.width / 200 - 1; x <= tileX + hitbox.width / 200 + 1; x++) {
                for (int y = tileY - hitbox.height / 200 - 1; y <= tileY + hitbox.height / 200 + 1; y++) {
                    if (x < 0 || y < 0 || x >= collision.length || y >= collision[0].length) continue;
                    if (!collision[x][y]) continue;
                    Rectangle tileHitbox = new Rectangle(x * 100, y * 100, 100, 100);
                    if (tileHitbox.intersects(hitbox)) {
                        Rectangle upCollision = new Rectangle(hitbox.x, hitbox.y, hitbox.width, 1);
                        if (upCollision.intersects(tileHitbox)) {
                            hitbox.y = tileHitbox.y + tileHitbox.height;
                            jumpingUpward = false;
                        }
                        else {
                            hitbox.y = tileHitbox.y - hitbox.height;
                            inAir = false;
                        }
                        speedY = 0;
                    }
                }
            }
            hitbox.x += (speedX + windSpeed) * delta;
            tileX = (hitbox.x + hitbox.width / 2) / 100;
            tileY = (hitbox.y + hitbox.height / 2) / 100;
            for (int x = tileX - hitbox.width / 200 - 1; x <= tileX + hitbox.width / 200 + 1; x++) {
                for (int y = tileY - hitbox.height / 200 - 1; y <= tileY + hitbox.height / 200 + 1; y++) {
                    if (x < 0 || y < 0 || x >= collision.length || y >= collision[0].length) continue;
                    Rectangle tileHitbox = new Rectangle(x * 100, y * 100, 100, 100);
                    if (tileHitbox.intersects(hitbox)) {
                        LevelTile tile = level.getTileList().get(level.getTileAt(x, y));
                        Rectangle leftCollision = new Rectangle(hitbox.x, hitbox.y, 1, hitbox.height);
                        tile.onTouch(entity, level, x, y);
                        if (leftCollision.intersects(tileHitbox)) {
                            tile.onTouchRight(entity, level, x, y);
                            entity.onTileTouchLeft(level, x, y);
                        }
                        else {
                            tile.onTouchLeft(entity, level, x, y);
                            entity.onTileTouchRight(level, x, y);
                        }
                    }
                }
            }
            for (Entity entity : new ArrayList<>(Arrays.asList(level.getEntityManager().array()))) {
                if (this.entity == entity || !entity.getPhysics().getConfig().solidHitbox) continue;
                Rectangle entityHitbox = entity.getPhysics().hitbox;
                if (hitbox.intersects(entityHitbox)) {
                    Rectangle leftCollision = new Rectangle(hitbox.x, hitbox.y, 1, hitbox.height);
                    boolean collisionOnLeft = leftCollision.intersects(entityHitbox);
                    if (((GameEntity)entity).getBehavior(PushableBehavior.class) == null) speedX = 0;
                    else {
                        if (speedX > maxSpeed / 4) speedX = maxSpeed / 4;
                        if (speedX < -maxSpeed / 4) speedX = -maxSpeed / 4;
                    }
                    int x = (entityHitbox.x + entityHitbox.width / 2) / 100;
                    int y = (entityHitbox.y + entityHitbox.height / 2) / 100;
                    entity.onEntityCollide(level, this.entity);
                    this.entity.onEntityCollide(level, entity);
                    int prevTile = level.getTileAt(x, y);
                    level.setTileAt(Tiles.BARRIER, x, y);
                    if (collisionOnLeft) {
                        hitbox.x = entityHitbox.x + entityHitbox.width;
                        this.entity.onTileTouchLeft(level, x, y);
                    }
                    else {
                        hitbox.x = entityHitbox.x - hitbox.width;
                        this.entity.onTileTouchRight(level, x, y);
                    }
                    level.setTileAt(prevTile, x, y);
                }
            }
            boolean isWindAndTouchingRightWall = false;
            for (int x = tileX - hitbox.width / 200 - 1; x <= tileX + hitbox.width / 200 + 1; x++) {
                for (int y = tileY - hitbox.height / 200 - 1; y <= tileY + hitbox.height / 200 + 1; y++) {
                    if (x < 0 || y < 0 || x >= collision.length || y >= collision[0].length) continue;
                    if (!collision[x][y]) continue;
                    Rectangle tileHitbox = new Rectangle(x * 100, y * 100, 100, 100);
                    if (tileHitbox.intersects(hitbox)) {
                        Rectangle leftCollision = new Rectangle(hitbox.x, hitbox.y, 1, hitbox.height);
                        hitbox.x = leftCollision.intersects(tileHitbox) ? tileHitbox.x + tileHitbox.width : tileHitbox.x - hitbox.width;
                        if (hitbox.x == tileHitbox.x - hitbox.width) isWindAndTouchingRightWall = ((GameLevel)level).gimmick == GameLevel.Gimmick.WIND;
                        speedX = 0;
                    }
                }
            }
            accelerationFactor = isWindAndTouchingRightWall ? 7.5 : 1;
        }
        else {
            hitbox.x += speedX * delta;
            hitbox.y += speedY * delta;
            int x = hitbox.x / 100;
            int y = hitbox.y / 100;
            if (x >= 0 && y >= 0 && x < collision.length && y < collision[0].length) level.getTileList().get(level.getTileAt(x, y)).onTouch(entity, level, x, y);
        }
        Entity[] entities = level.getEntityManager().array();
        for (Entity entity : entities) {
            if (entity == this.entity) continue;
            Rectangle hitbox = entity.getPhysics().getHitbox();
            if (this.hitbox.intersects(hitbox)) {
                this.entity.onEntityCollide(level, entity);
            }
        }
        speedY += (config.underwater ? config.underwaterGravity : config.gravity);
        if (speedY >= config.terminalVelocity) speedY = config.terminalVelocity;
        if (!fixedSpeedX) {
            double acceleration = config.underwater ? config.underwaterAcceleration : config.acceleration;
            float speedFactor = ((GameEntity)entity).entityType == EntityType.PLAYER && Game.savefile.powerupState == 5 ? 1.5f : 1f;
            if (((GameEntity)entity).entityType == EntityType.PLAYER && Game.invincibilityTimeout > 0) speedFactor *= 1.25f;
            maxSpeed *= speedFactor;
            acceleration *= accelerationFactor * (((GameLevel)((GameEntity)entity).getLevel()).gimmick == GameLevel.Gimmick.SLIPPERY ? 0.5f : 1f);
            if (movement.isWalkingLeft() && !movement.isWalkingRight()) {
                speedX -= acceleration;
                if (speedX < -maxSpeed) speedX = -maxSpeed;
            }
            else if (movement.isWalkingRight() && !movement.isWalkingLeft()) {
                speedX += acceleration;
                if (speedX > maxSpeed) speedX = maxSpeed;
            }
            else {
                if (speedX < 0) {
                    speedX += acceleration;
                    if (speedX > 0) speedX = 0;
                }
                if (speedX > 0) {
                    speedX -= acceleration;
                    if (speedX < 0) speedX = 0;
                }
            }
        }
        float jumpFactor = ((GameEntity)entity).entityType == EntityType.PLAYER && Game.savefile.powerupState == 4 ? 1.5f : 1f;
        if (movement.isJumping() && !jumping) {
            jump(true);
            jumping = true;
        }
        else jumping = false;
        if (jumpingUpward && jumping) {
            if (!movement.isJumping()) jumpingUpward = false;
            if (hitbox.getY() >= jumpOrigin - config.maxJumpHeight * jumpFactor) speedY = -config.jumpingSpeed;
            else jumpingUpward = false;
        }
        if (inAir && !movement.isJumping()) jumpingUpward = false;
        speedX = nextSpeedX == null ? speedX : nextSpeedX;
        speedY = nextSpeedY == null ? speedY : nextSpeedY;
        nextSpeedX = null;
        nextSpeedY = null;
        return this;
    }
    public void jump(boolean playSound) {
        if ((inAir && !config.underwater) || jumping) return;
        if (config.underwater) speedY = config.underwaterSwimSpeed;
        else {
            if (playSound) AudioPlayer.JUMP.play();
            jumpOrigin = hitbox.getY();
            jumpingUpward = true;
        }
    }
    public Physics setSpeedX(double speed) {
        speedX = speed;
        nextSpeedX = speed;
        return this;
    }
    public Physics setSpeedY(double speed) {
        speedY = speed;
        nextSpeedY = speed;
        return this;
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public PlayerMovement getMovement() {
        return movement;
    }
    public PhysicsConfig getConfig() {
        return config;
    }
    public boolean begun() {
        return begun;
    }
    public Level getLevel() {
        return level;
    }
    public boolean isInAir() {
        return inAir;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    public void updateCollisionMap() {
        for (int x = 0; x < collision.length; x++) {
            for (int y = 0; y < collision[x].length; y++) {
                collision[x][y] = level.getTileList().get(level.getTileAt(x, y)).isSolid();
            }
        }
    }
    public void setConfig(PhysicsConfig config) {
        this.config = config;
    }
}
