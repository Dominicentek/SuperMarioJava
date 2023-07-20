package com.smj.game.tile;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;

public class EntitySpawnerTile extends GameTile {
    private EntityType[] types;
    public EntitySpawnerTile(EntityType... types) {
        this.types = types;
    }
    public void init(GameLevel level, int x, int y) {
        level.setTileAt(Tiles.AIR, x, y);
        for (EntityType type : types) {
            type.spawn(level, x * 100 + 50 - type.getHitbox().width / 2, y * 100 + 100 - type.getHitbox().height);
        }
    }
}
