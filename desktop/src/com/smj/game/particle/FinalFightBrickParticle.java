package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;

import java.awt.*;

public class FinalFightBrickParticle extends Particle {
    public int x;
    public int y;
    public int timeout = 5;
    public int rotation = 0;
    public FinalFightBrickParticle(int x, int y) {
        super(TextureLoader.get("images/particles/brick.png"));
        this.x = x;
        this.y = y;
    }
    public void update() {
        y += 4;
        timeout--;
        if (timeout == 0) {
            timeout = 5;
            rotation++;
            if (rotation == 4) rotation = 0;
        }
        if (y == 0) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(8 * rotation, 0, 8, 8);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
