package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.smj.util.TextureLoader;

import java.awt.Point;
import java.awt.Rectangle;

public class IceExplosionParticle extends ExplosionParticle {
    public IceExplosionParticle(int x, int y) {
        super(x, y);
        texture = TextureLoader.get(Gdx.files.internal("assets/images/particles/ice_explosion.png"));
    }
}
