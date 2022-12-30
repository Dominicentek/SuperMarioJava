package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.BlooperBehavior;

import java.awt.Rectangle;

public class BlooperTextureProvider extends TextureProvider {
    public BlooperTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        int width = getTexture().getWidth() / 2;
        int height = getTexture().getHeight();
        int x = 0;
        if (entity.getBehavior(BlooperBehavior.class).chargeTimeout != 0) x = 16;
        return new Rectangle(x, 0, width, height);
    }
}
