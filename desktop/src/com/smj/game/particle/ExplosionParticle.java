package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class ExplosionParticle extends Particle {
    public int explosionStage = 0;
    public int timeout = 3;
    public int x;
    public int y;
    public ExplosionParticle(int x, int y) {
        super(TextureLoader.get("images/particles/explosion.png"));
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (timeout > 0) {
            timeout--;
            if (timeout == 0) {
                timeout = 3;
                explosionStage++;
                if (explosionStage == 4) despawn();
            }
        }
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(explosionStage * 18, 0, 18, 18);
    }
    public Point getPosition() {
        return new Point(x, y);
    }
}
