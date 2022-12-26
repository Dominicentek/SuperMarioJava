package com.smj.jmario.tile.level;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.level.Level;

public abstract class LevelTile {
    private boolean solid = false;
    private boolean decorable = false;
    private float accelerationFactor = 1f;
    private Texture texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
    public abstract void onTouch(Entity entity, Level level, int x, int y);
    public abstract void onTouchTop(Entity entity, Level level, int x, int y);
    public abstract void onTouchLeft(Entity entity, Level level, int x, int y);
    public abstract void onTouchRight(Entity entity, Level level, int x, int y);
    public abstract void onTouchBottom(Entity entity, Level level, int x, int y);
    public abstract void init(Level level, int x, int y);
    public abstract void update(Level level, int x, int y);
    public LevelTile setSolid(boolean solid) {
        this.solid = solid;
        return this;
    }
    public LevelTile setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }
    public LevelTile setDecorable(boolean decorable) {
        this.decorable = decorable;
        return this;
    }
    public LevelTile setAccelerationFactor(float accelerationFactor) {
        this.accelerationFactor = accelerationFactor;
        return this;
    }
    public Texture getTexture() {
        return texture;
    }
    public boolean isSolid() {
        return solid;
    }
    public boolean isDecorable() {
        return decorable;
    }
    public float getAccelerationFactor() {
        return accelerationFactor;
    }
}
