package com.smj.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smj.gui.font.Font;

import java.awt.Rectangle;

public class Renderer extends SpriteBatch {
    public static final Texture pixel;
    private int translateX;
    private int translateY;
    public int height;
    public boolean topLeft = true;
    static {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.drawPixel(0, 0, 0xFFFFFFFF);
        pixel = new Texture(pixmap);
    }
    public Renderer(int height) {
        this.height = height;
    }
    public void setColor(int rgba) {
        setColor(((rgba >> 24) & 0xFF) / 255f, ((rgba >> 16) & 0xFF) / 255f, ((rgba >> 8) & 0xFF) / 255f, (rgba & 0xFF) / 255f);
    }
    public void draw(Texture texture, int x, int y) {
        draw(texture, x, y, texture.getWidth(), texture.getHeight(), false, false);
    }
    public void draw(Texture texture, int x, int y, int width, int height) {
        draw(texture, x, y, width, height, false, false);
    }
    public void draw(Texture texture, int x, int y, boolean flipX, boolean flipY) {
        draw(texture, x, y, texture.getWidth(), texture.getHeight(), flipX, flipY);
    }
    public void draw(Texture texture, int x, int y, int width, int height, boolean flipX, boolean flipY) {
        draw(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), x, y, width, height, flipX, flipY);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y) {
        draw(texture, bounds, x, y, bounds.width, bounds.height, false, false);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, int width, int height) {
        draw(texture, bounds, x, y, width, height, false, false);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, boolean flipX, boolean flipY) {
        draw(texture, bounds, x, y, bounds.width, bounds.height, flipX, flipY);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, int width, int height, boolean flipX, boolean flipY) {
        if (topLeft) y = this.height - y - height;
        draw(texture, x + translateX, y + translateY, width, height, bounds.x, bounds.y, bounds.width, bounds.height, flipX, flipY);
    }
    public void rect(int x, int y, int width, int height) {
        draw(pixel, x, y, width, height);
    }
    public void rect(int x, int y, int width, int height, int color) {
        Color prevColor = getColor();
        setColor(color);
        rect(x, y, width, height);
        setColor(prevColor);
    }
    public void translate(int x, int y) {
        translateX += x;
        translateY -= y;
    }
    public void resetTranslation() {
        translateX = 0;
        translateY = 0;
    }
    public void drawString(String text, int x, int y) {
        Font.render(this, x, y, text);
    }
    public void drawString(String text, int x, int y, float scale) {
        Font.render(this, x, y, text, scale);
    }
}