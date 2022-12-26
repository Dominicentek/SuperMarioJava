package com.smj.game.particle;

import com.smj.game.Game;
import com.smj.util.RNG;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class SnowParticle extends Particle {
    public int x;
    public int y;
    public int timeout = 3;
    public SnowParticle(int x, int y) {
        super(TextureLoader.getPlainTexture(1, 1, 0xEFEFFFFF));
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (Game.paused) return;
        timeout--;
        if (timeout == 0) {
            timeout = 3;
            y++;
            x += RNG.range(-1, 1);
        }
        if (x < 0 || y < 0 || x >= Game.currentLevel.getLevelBoundaries().width * 16 || y >= Game.currentLevel.getLevelBoundaries().height * 16) despawn();
        else if (Game.currentLevel.getTileList().get(Game.currentLevel.getTileAt(x / 16, y / 16)).isSolid()) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, 1, 1);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
