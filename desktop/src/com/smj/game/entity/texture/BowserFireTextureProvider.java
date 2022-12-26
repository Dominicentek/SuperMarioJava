package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class BowserFireTextureProvider extends TextureProvider {
    private int timeout = 2;
    public BowserFireTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        timeout--;
        if (timeout == 0) {
            timeout = 2;
            flipY = !flipY;
        }
        return new Rectangle(0, 0, getTexture().getWidth(), getTexture().getHeight());
    }
}
