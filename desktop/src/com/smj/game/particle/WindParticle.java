package com.smj.game.particle;

import com.smj.game.Game;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class WindParticle extends Particle {
    public int x;
    public int y;
    public int timeout = 3;
    public WindParticle(int y) {
        super(TextureLoader.getPlainTexture(1, 1, 0xFFAA47FF));
        this.y = y;
    }
    public WindParticle(int x, int y) {
        this(y);
        this.x = x;
    }
    public void update() {
        if (Game.paused) return;
        x++;
        if (x == Game.currentLevel.getLevelBoundaries().width * 16) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, 1, 1);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
