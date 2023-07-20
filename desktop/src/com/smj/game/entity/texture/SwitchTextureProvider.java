package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.FinalFightSwitchBehavior;

import java.awt.*;

public class SwitchTextureProvider extends TextureProvider {
    public SwitchTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        if (entity.getBehavior(FinalFightSwitchBehavior.class).pressed) return new Rectangle(16, 0, 16, 16);
        return new Rectangle(0, 0, 16, 16);
    }
}
