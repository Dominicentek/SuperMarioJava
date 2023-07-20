package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.Game;
import com.smj.game.entity.GameEntity;

import java.awt.*;

public class BigBowserTextureProvider extends StaticTextureProvider {
    public BigBowserTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        scaleX = 6;
        scaleY = 6;
        flipY = Game.finalFightSwitchesPressed == 3;
        return super.getTextureRegion(entity);
    }
}
