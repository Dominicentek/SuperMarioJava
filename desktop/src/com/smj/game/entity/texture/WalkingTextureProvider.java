package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class WalkingTextureProvider extends AnimatedTextureProvider {
    public WalkingTextureProvider(Texture texture, Integer frames) {
        super(texture, frames);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        if (entity.getPhysics().getSpeedX() < 0) flipX = false;
        if (entity.getPhysics().getSpeedX() > 0) flipX = true;
        return super.getTextureRegion(entity);
    }
}
