package com.smj.game.tile;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;

public class EntitySpawnerTile extends GameTile {
    private EntityType type;
    public EntitySpawnerTile(EntityType type) {
        this.type = type;
    }
    public void init(GameLevel level, int x, int y) {
        level.setTileAt(Tiles.AIR, x, y);
        type.spawn(level, x * 100 + 50 - type.getHitbox().width / 2, y * 100 + 100 - type.getHitbox().height);
    }
}
