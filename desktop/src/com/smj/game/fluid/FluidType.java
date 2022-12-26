package com.smj.game.fluid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;

public enum FluidType {
    WATER(TextureLoader.get(Gdx.files.internal("assets/images/fluids/water.png"))),
    POISON(TextureLoader.get(Gdx.files.internal("assets/images/fluids/poison.png"))),
    LAVA(TextureLoader.get(Gdx.files.internal("assets/images/fluids/lava.png"))),
    ;
    public Texture texture;
    FluidType(Texture texture) {
        this.texture = texture;
    }
}
