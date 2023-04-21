package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.BumpTileBehavior;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.level.Level;
import com.smj.jmario.tile.level.LevelTile;

import java.util.ArrayList;
import java.util.Arrays;

public class GameTile extends LevelTile {
    public ArrayList<Integer> textureLocations = new ArrayList<>();
    public int currentLocationIndex = 0;
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {}
    public void onTouchTop(GameEntity entity, GameLevel level, int x, int y) {}
    public void onTouchLeft(GameEntity entity, GameLevel level, int x, int y) {}
    public void onTouchRight(GameEntity entity, GameLevel level, int x, int y) {}
    public void onTouchBottom(GameEntity entity, GameLevel level, int x, int y) {}
    public void init(GameLevel level, int x, int y) {}
    public void update(GameLevel level, int x, int y) {}
    public final void onTouch(Entity entity, Level level, int x, int y) {
        onTouch((GameEntity)entity, (GameLevel)level, x, y);
    }
    public final void onTouchTop(Entity entity, Level level, int x, int y) {
        onTouchTop((GameEntity)entity, (GameLevel)level, x, y);
    }
    public final void onTouchLeft(Entity entity, Level level, int x, int y) {
        onTouchLeft((GameEntity)entity, (GameLevel)level, x, y);
    }
    public final void onTouchRight(Entity entity, Level level, int x, int y) {
        onTouchRight((GameEntity)entity, (GameLevel)level, x, y);
    }
    public final void onTouchBottom(Entity entity, Level level, int x, int y) {
        onTouchBottom((GameEntity)entity, (GameLevel)level, x, y);
    }
    public final void init(Level level, int x, int y) {
        init((GameLevel)level, x, y);
    }
    public final void update(Level level, int x, int y) {
        update((GameLevel)level, x, y);
    }
    public GameTile setTextureLocations(Integer... locations) {
        textureLocations.clear();
        currentLocationIndex = 0;
        textureLocations.addAll(Arrays.asList(locations));
        return this;
    }
    public void cycleTexture() {
        currentLocationIndex++;
        if (currentLocationIndex == textureLocations.size()) currentLocationIndex = 0;
    }
    public int getCurrentTextureLocation() {
        return textureLocations.get(currentLocationIndex);
    }
    public static void bump(GameLevel level, int x, int y) {
        bump(level, x, y, () -> {});
    }
    public static void bump(GameLevel level, int x, int y, Runnable after) {
        int tileID = level.getTileAt(x, y);
        GameEntity entity = EntityType.BUMP_TILE.spawn(level, x * 100, y * 100);
        entity.getBehavior(BumpTileBehavior.class).tile = tileID;
        entity.getBehavior(BumpTileBehavior.class).after = after;
        entity.updateTexture();
    }
}
