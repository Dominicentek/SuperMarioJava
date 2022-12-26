package com.smj.jmario.level.decoration;

public enum DecorationType {
    SHORT(1, 1), NORMAL(1, 2), TALL(1, 3), WIDE(3, 1);
    private int width;
    private int height;
    DecorationType(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
