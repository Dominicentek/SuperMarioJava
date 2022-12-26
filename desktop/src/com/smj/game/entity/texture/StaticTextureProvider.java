package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;

public class StaticTextureProvider extends AnimatedTextureProvider {
    public StaticTextureProvider(Texture texture) {
        super(texture, 1);
    }
}
