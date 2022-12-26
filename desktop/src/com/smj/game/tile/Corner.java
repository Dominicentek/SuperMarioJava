package com.smj.game.tile;

public enum Corner {
    TOP_LEFT(0, 0),
    TOP_RIGHT(-1, 0),
    BOTTOM_LEFT(0, -1),
    BOTTOM_RIGHT(-1, -1);
    public final int toTopLeftX;
    public final int toTopLeftY;
    Corner(int toTopLeftX, int toTopLeftY) {
        this.toTopLeftX = toTopLeftX;
        this.toTopLeftY = toTopLeftY;
    }
}
