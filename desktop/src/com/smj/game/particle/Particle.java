package com.smj.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.Game;
import com.smj.game.Location;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Particle {
    public Texture texture;
    public Particle(Texture texture) {
        this.texture = texture;
    }
    public abstract void update();
    public abstract Rectangle getTextureRegion();
    public abstract Point getPosition();
    public int getTextureAlpha() {
        return 255;
    }
    public final void despawn() {
        Game.particles.remove(this);
    }
}
