package com.smj.game.fluid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;

public enum FluidType {
    WATER(0x0000FF3F),
    POISON(0xBF00BF7F),
    LAVA(0xBF0000FF);
    public final int color;
    FluidType(int color) {
        this.color = color;
    }
}
