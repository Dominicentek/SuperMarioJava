package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class BlankTextureProvider extends TextureProvider {
    public BlankTextureProvider() {
        super(null);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        return new Rectangle();
    }
}
