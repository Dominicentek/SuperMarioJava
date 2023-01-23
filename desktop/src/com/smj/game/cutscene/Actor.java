package com.smj.game.cutscene;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Rectangle;

public class Actor {
    public final int initX;
    public final int initY;
    public int x;
    public int y;
    public Texture texture;
    public Rectangle region;
    public Actor(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        initX = x;
        initY = y;
        this.texture = texture;
        this.region = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public Actor restore() {
        x = initX;
        y = initY;
        region = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        return this;
    }
}
