package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.Entity;

import java.awt.Point;
import java.awt.Rectangle;

public class FallingEnemyParticle extends Particle {
    public GameEntity entity;
    public int x;
    public double y;
    public double speedY;
    public FallingEnemyParticle(GameEntity entity) {
        super(entity.getProperties().texture);
        x = (entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) * 16 / 100;
        y = (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2.0) * 16 / 100;
        speedY = -3;
        this.entity = entity;
    }
    public void update() {
        speedY += 0.20;
        y += speedY;
        if (y >= entity.getLevel().getLevelBoundaries().height * 16 + 16) despawn();
    }
    public Rectangle getTextureRegion() {
        return entity.provider.getTextureRegion(entity);
    }
    public Point getPosition() {
        return new Point(x, (int)y);
    }
}
