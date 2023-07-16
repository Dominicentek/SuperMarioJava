package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.Game;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.Entity;
import com.smj.util.RNG;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class AcidRainParticle extends Particle {
    private int x;
    private int y;
    private int height;
    private int speed;
    public AcidRainParticle(int x) {
        super(TextureLoader.getPlainTexture(1, RNG.range(3, 7), 0x3FFF3FFF));
        this.height = texture.getHeight();
        this.speed = RNG.range(2, 3);
        this.x = x;
        this.y = -height;
    }
    public void update() {
        if (Game.paused) return;
        y += speed;
        int ty = (y + height / 2) / 16;
        int tx = x / 16;
        if (Game.currentLevel.getTileList().get(Game.currentLevel.getTileAt(tx, ty)).isSolid()) despawn();
        if (y - height / 2 > Game.currentLevel.getLevelBoundaries().height * 16) despawn();
        Rectangle hitbox = new Rectangle(x * 100 / 16, (y - height / 2) * 100 / 16, 100 / 16, height * 100 / 16);
        for (Entity entity : Game.currentLevel.getEntityManager()) {
            if (!entity.getPhysics().getHitbox().intersects(hitbox)) continue;
            if (entity.getPhysics().getConfig().solidHitbox) despawn();
            if (((GameEntity)entity).entityType == EntityType.PLAYER) Game.damagePlayer();
        }
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
