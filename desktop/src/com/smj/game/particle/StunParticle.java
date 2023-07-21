package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.Condition;
import com.smj.util.TextureLoader;

import java.awt.*;

public class StunParticle extends Particle {
    public int x;
    public int y;
    public Condition disappearCondition;
    public int frameTimeout = 3;
    public boolean flipped;
    public boolean frame;
    public StunParticle(int x, int y, boolean flipped, Condition disappearCondition) {
        super(TextureLoader.get("images/particles/stun.png"));
        this.x = x;
        this.y = y;
        this.flipped = flipped;
        this.disappearCondition = disappearCondition;
    }
    public void update() {
        frameTimeout--;
        if (frameTimeout == 0) {
            frameTimeout = 3;
            frame = !frame;
        }
        if (disappearCondition.eval()) despawn();
    }
    public Rectangle getTextureRegion() {
        int frame = (flipped ? 2 : 0) + (this.frame ? 1 : 0);
        return new Rectangle(8 * frame, 0, 8, 8);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
