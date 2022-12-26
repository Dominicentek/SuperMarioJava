package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.FlyingBehavior;

import java.awt.Rectangle;

public class FlyingTextureProvider extends StaticTextureProvider {
    public FlyingTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        flipX = entity.getBehavior(FlyingBehavior.class).speed < 0;
        return super.getTextureRegion(entity);
    }
}
