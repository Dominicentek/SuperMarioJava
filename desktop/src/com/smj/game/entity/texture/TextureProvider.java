package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public abstract class TextureProvider {
    private final Texture texture;
    public boolean flipX;
    public boolean flipY;
    public float scaleX = 1;
    public float scaleY = 1;
    public TextureProvider(Texture texture) {
        this.texture = texture;
    }
    public abstract Rectangle getTextureRegion(GameEntity entity);
    public final Texture getTexture() {
        return texture;
    }
}
