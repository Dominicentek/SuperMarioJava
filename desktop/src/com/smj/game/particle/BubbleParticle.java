package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.RNG;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class BubbleParticle extends Particle {
    public double alpha = 255;
    public double alphaTimeout;
    public double x;
    public double y;
    public double speedX;
    public double speedY;
    public BubbleParticle(int x, int y, double speedX, double speedY) {
        super(TextureLoader.get(Gdx.files.internal("assets/images/particles/bubble.png")));
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        alphaTimeout = RNG.range(1.0, 2.0);
        noUpdatePaused = true;
    }
    public void update() {
        alpha -= alphaTimeout;
        if (alpha <= 0) despawn();
        x += speedX;
        y += speedY;
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public Point getPosition() {
        return new Point((int)x, (int)y);
    }
    public int getTextureAlpha() {
        return (int)alpha;
    }
}
