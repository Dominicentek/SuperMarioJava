package com.smj.game.entity.behavior;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public interface EntityBehavior {
    default void update(GameEntity entity, GameLevel level) {}
    default void onLoad(GameEntity entity, GameLevel level) {}
    default void onUnload(GameEntity entity, GameLevel level) {}
    default void onTileTouchUp(GameEntity entity, GameLevel level, int x, int y) {}
    default void onTileTouchLeft(GameEntity entity, GameLevel level, int x, int y) {}
    default void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {}
    default void onTileTouchRight(GameEntity entity, GameLevel level, int x, int y) {}
    default void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {}
}
