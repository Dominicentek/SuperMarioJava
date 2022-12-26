package com.smj.game.particle;

import com.smj.gui.font.Font;

import java.awt.Point;
import java.awt.Rectangle;

public class RisingTextParticle extends Particle {
    public int timeout = 60;
    public int x;
    public int y;
    public RisingTextParticle(String text, int x, int y) {
        super(Font.getTexture(text));
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (timeout > 0) timeout--;
        if (timeout == 0) despawn();
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public Point getPosition() {
        return new Point(x, y - (60 - timeout));
    }
    public int getTextureAlpha() {
        return timeout * 255 / 30;
    }
}
