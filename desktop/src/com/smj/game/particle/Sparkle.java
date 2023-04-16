package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class Sparkle extends Particle {
    public int x;
    public int y;
    public int frame = 0;
    public int timeout = 5;
    public Sparkle(int x, int y) {
        super(TextureLoader.get("images/particles/sparkles.png"));
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (timeout > 0) {
            timeout--;
            return;
        }
        timeout = 5;
        frame++;
        if (frame == 5) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(frame * 7, 0, 7, 7);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
