package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.BigBowserFireBehavior;

import java.awt.*;

public class BigBowserFireTextureProvider extends TextureProvider {
    public int timeout = 15;
    public int stage = 0;
    public BigBowserFireTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        timeout--;
        if (timeout == 0) {
            timeout = 15;
            stage++;
            if (stage == 4) stage = 0;
            flipX = stage == 1 || stage == 2;
            flipY = stage == 2 || stage == 3;
        }
        float scale = entity.getBehavior(BigBowserFireBehavior.class).scale;
        scaleX = scale;
        scaleY = scale;
        return new Rectangle(0, 0, 16, 16);
    }
}
