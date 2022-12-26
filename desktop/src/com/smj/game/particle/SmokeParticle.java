package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class SmokeParticle extends Particle {
    public int explosionStage = 0;
    public int timeout = 5;
    public int x;
    public int y;
    public SmokeParticle(int x, int y) {
        super(TextureLoader.get(Gdx.files.internal("assets/images/particles/smoke.png")));
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (timeout > 0) {
            timeout--;
            if (timeout == 0) {
                timeout = 5;
                explosionStage++;
                if (explosionStage == 4) despawn();
            }
        }
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(explosionStage * 16, 0, 16, 16);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
