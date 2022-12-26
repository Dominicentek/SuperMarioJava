package com.smj.jmario.level.decoration;

import com.badlogic.gdx.graphics.Texture;

public class Decoration {
    private DecorationType type;
    private Texture texture;
    public Decoration(Texture texture, DecorationType type) {
        this.type = type;
        this.texture = texture;
    }
    public Decoration setType(DecorationType type) {
        this.type = type;
        return this;
    }
    public Decoration setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }
    public DecorationType getType() {
        return type;
    }
    public Texture getTexture() {
        return texture;
    }
}
