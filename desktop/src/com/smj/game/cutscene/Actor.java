package com.smj.game.cutscene;

import com.badlogic.gdx.graphics.Texture;

public class Actor {
    public final int initX;
    public final int initY;
    public int x;
    public int y;
    public Texture texture;
    public Actor(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        initX = x;
        initY = y;
        this.texture = texture;
    }
    public Actor restorePos() {
        x = initX;
        y = initY;
        return this;
    }
}
