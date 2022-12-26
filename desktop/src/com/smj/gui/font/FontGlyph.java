package com.smj.gui.font;


public class FontGlyph {
    private int width;
    private boolean[][] data;
    private char character;
    public FontGlyph(int width, boolean[][] data, char character) {
        this.width = width;
        this.data = data;
        this.character = character;
    }
    public int getWidth() {
        return width;
    }
    public boolean[][] getData() {
        return data;
    }
    public char getCharacter() {
        return character;
    }
}
