package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.SmokeParticle;
import com.smj.game.tile.BrickTile;
import com.smj.game.tile.GameTile;
import com.smj.game.tile.Tiles;
import com.smj.util.AudioPlayer;

public class MeteoriteBehavior implements EntityBehavior {
    public static final int[] BREAKABLE_TILES = {
        Tiles.QUESTION_BLOCK_COIN,
        Tiles.QUESTION_BLOCK_MUSHROOM,
        Tiles.QUESTION_BLOCK_FIRE_FLOWER,
        Tiles.QUESTION_BLOCK_ICE_FLOWER,
        Tiles.QUESTION_BLOCK_JUMP_SHOES,
        Tiles.QUESTION_BLOCK_SPEED_SHOES,
        Tiles.QUESTION_BLOCK_1UP,
        Tiles.QUESTION_BLOCK_POISON_MUSHROOM,
        Tiles.QUESTION_BLOCK_SUPERSTAR,
        Tiles.BRICK_EMPTY,
        Tiles.BRICK_COIN,
        Tiles.BRICK_MUSHROOM,
        Tiles.BRICK_FIRE_FLOWER,
        Tiles.BRICK_ICE_FLOWER,
        Tiles.BRICK_JUMP_SHOES,
        Tiles.BRICK_SPEED_SHOES,
        Tiles.BRICK_1UP,
        Tiles.BRICK_POISON_MUSHROOM,
        Tiles.BRICK_SUPERSTAR,
        Tiles.FRAGILE_BRICK,
        Tiles.EMPTY_BLOCK,
        Tiles.STONE_BLOCK,
        Tiles.ICE,
        Tiles.FROZEN_COIN
    };
    public void update(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.getPhysics().setSpeedY(0.78125);
    }
    public void onTileTouchDown(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getEntityManager().entities.contains(entity)) return;
        if (level.getTileList().get(level.getTileAt(x, y)).isSolid()) {
            if (isBreakable(level.getTileAt(x, y))) {
                BrickTile.destroy(level, x, y);
            }
            level.getEntityManager().unloadEntity(entity);
            int X = entity.getPhysics().getHitbox().x;
            int Y = entity.getPhysics().getHitbox().y;
            Game.particles.add(new SmokeParticle((X + 50) * 16 / 100, (Y + 50) * 16 / 100));
            AudioPlayer.BRICK.play();
            EntityType.METEORITE_FRAGMENT.spawn(level, X, Y);
            EntityType.METEORITE_FRAGMENT.spawn(level, X + 50, Y);
            EntityType.METEORITE_FRAGMENT.spawn(level, X, Y + 50);
            EntityType.METEORITE_FRAGMENT.spawn(level, X + 50, Y + 50);
        }
    }
    public boolean isBreakable(int tile) {
        for (int breakableTile : BREAKABLE_TILES) {
            if (tile == breakableTile) return true;
        }
        return false;
    }
}
