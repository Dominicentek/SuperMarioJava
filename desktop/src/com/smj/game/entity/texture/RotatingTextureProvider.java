package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class RotatingTextureProvider extends TextureProvider {
    private int state = 0;
    private int timeout = 10;
    public RotatingTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        timeout--;
        if (timeout == 0) {
            timeout = 10;
            state++;
            if (state == 4) state = 0;
            flipX = state == 1 || state == 2;
            flipY = state == 2 || state == 3;
        }
        return new Rectangle(0, 0, getTexture().getWidth(), getTexture().getHeight());
    }
}
