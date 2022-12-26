package com.smj.jmario.entity;

import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.physics.Physics;
import com.smj.jmario.entity.physics.PhysicsConfig;
import com.smj.jmario.level.Level;

public abstract class Entity {
    private Physics physics;
    private EntityProperties properties;
    public Entity(PhysicsConfig physicsConfig, EntityProperties properties) {
        this.physics = new Physics(this, physicsConfig);
        this.properties = properties;
    }
    public abstract void update(Level level);
    public abstract void onLoad(Level level);
    public abstract void onUnload(Level level);
    public abstract void onTileTouchUp(Level level, int x, int y);
    public abstract void onTileTouchLeft(Level level, int x, int y);
    public abstract void onTileTouchDown(Level level, int x, int y);
    public abstract void onTileTouchRight(Level level, int x, int y);
    public abstract void onEntityCollide(Level level, Entity entity);
    public final double[] getTextureOrigin(int unitWidth, int unitHeight) {
        return new double[]{physics.getHitbox().x / 100.0 + physics.getHitbox().width / 200.0 - (this instanceof GameEntity ? ((GameEntity)this).textureRegion.width : properties.texture.getWidth()) / (double)unitWidth / 2, physics.getHitbox().y / 100.0 + physics.getHitbox().height / 100.0 - (this instanceof GameEntity ? ((GameEntity)this).textureRegion.height : properties.texture.getHeight()) / (double)unitHeight};
    }
    public final EntityProperties getProperties() {
        return properties;
    }
    public final Physics getPhysics() {
        return physics;
    }
}
