package com.smj.jmario.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.smj.jmario.level.decoration.Decorations;

public class LevelBackground {
    private Color color;
    private Texture image;
    private Decorations decorations = new Decorations();
    public LevelBackground(Color color, Texture image) {
        setColor(color).setImage(image);
    }
    public LevelBackground setColor(Color color) {
        this.color = color;
        return this;
    }
    public LevelBackground setImage(Texture image) {
        this.image = image;
        return this;
    }
    public LevelBackground setDecorations(Decorations decorations) {
        this.decorations = decorations;
        return this;
    }
    public Color getColor() {
        return color;
    }
    public Texture getImage() {
        return image;
    }
    public Decorations getDecorations() {
        return decorations;
    }
}
