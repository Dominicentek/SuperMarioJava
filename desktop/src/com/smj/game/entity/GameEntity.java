package com.smj.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.behavior.EntityBehavior;
import com.smj.game.entity.behavior.WalkingBehavior;
import com.smj.game.entity.texture.TextureProvider;
import com.smj.game.score.SuccessionScore;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.entity.EntityProperties;
import com.smj.jmario.entity.physics.PhysicsConfig;
import com.smj.jmario.level.Level;
import com.smj.util.mask.Circle;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class GameEntity extends Entity {
    public ArrayList<EntityBehavior> behaviors = new ArrayList<>();
    public TextureProvider provider;
    public Rectangle textureRegion;
    public final EntityType entityType;
    public SuccessionScore score = new SuccessionScore();
    public boolean invisible = false;
    public boolean requiredToNotExistInEnemyFight = false;
    public int spawnOffsetX = 0;
    public int spawnOffsetY = 0;
    public Circle spotlight = null;
    public int priority = 0;
    public boolean immuneToDeathBarrier = false;
    public GameEntity(PhysicsConfig physicsConfig, EntityProperties properties, TextureProvider provider, EntityType type, EntityBehavior... behaviors) {
        super(physicsConfig, properties);
        this.provider = provider;
        this.behaviors.addAll(Arrays.asList(behaviors));
        this.entityType = type;
        Texture texture = provider.getTexture();
        textureRegion = texture == null ? new Rectangle() : new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        properties.setTexture(texture);
    }
    public Level getLevel() {
        return getPhysics().getLevel();
    }
    public void updateTexture() {
        textureRegion = provider.getTextureRegion(this);
    }
    public void update(Level level) {
        updateTexture();
        for (EntityBehavior behavior : behaviors) {
            behavior.update(this, (GameLevel)level);
        }
        if (spotlight != null) {
            Rectangle hitbox = getPhysics().getHitbox();
            spotlight.x = (hitbox.x + hitbox.width / 2) * 16 / 100;
            spotlight.y = (hitbox.y + hitbox.height / 2) * 16 / 100;
        }
    }
    public void onLoad(Level level) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onLoad(this, (GameLevel)level);
        }
        if (((GameLevel)level).gimmick != GameLevel.Gimmick.SPOTLIGHT) spotlight = null;
        if (spotlight != null) {
            Rectangle hitbox = getPhysics().getHitbox();
            spotlight.x = (hitbox.x + hitbox.width / 2) * 16 / 100;
            spotlight.y = (hitbox.y + hitbox.height / 2) * 16 / 100;
            Main.mask.add(spotlight);
        }
    }
    public void onUnload(Level level) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onUnload(this, (GameLevel)level);
        }
        Main.mask.remove(spotlight);
    }
    public void onTileTouchUp(Level level, int x, int y) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onTileTouchUp(this, (GameLevel)level, x, y);
        }
    }
    public void onTileTouchLeft(Level level, int x, int y) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onTileTouchLeft(this, (GameLevel)level, x, y);
        }
    }
    public void onTileTouchDown(Level level, int x, int y) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onTileTouchDown(this, (GameLevel)level, x, y);
        }
    }
    public void onTileTouchRight(Level level, int x, int y) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onTileTouchRight(this, (GameLevel)level, x, y);
        }
    }
    public void onEntityCollide(Level level, Entity entity) {
        for (EntityBehavior behavior : behaviors) {
            behavior.onEntityCollide(this, (GameLevel)level, (GameEntity)entity);
        }
    }
    public <T extends EntityBehavior> T getBehavior(Class<T> type) {
        for (EntityBehavior behavior : behaviors) {
            if (type.isInstance(behavior)) return type.cast(behavior);
        }
        return null;
    }
}
