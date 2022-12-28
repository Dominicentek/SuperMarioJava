package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class BrickTextureProvider extends RotatingTextureProvider {
    public BrickTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        if (entity.getPhysics().getSpeedX() == 0) return new Rectangle(0, 0, getTexture().getWidth(), getTexture().getHeight());
        return super.getTextureRegion(entity);
    }
}
