package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class MeteoriteIndicatorParticle extends Particle {
    public int x;
    public int timeout = 60;
    public MeteoriteIndicatorParticle(int x) {
        super(TextureLoader.get("images/particles/meteorite_indicator.png"));
        this.x = x;
    }
    public void update() {
        timeout--;
        if (timeout == 0) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public Point getPosition() {
        return new Point(x, 8);
    }
}
