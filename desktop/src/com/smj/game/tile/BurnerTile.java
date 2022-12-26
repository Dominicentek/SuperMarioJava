package com.smj.game.tile;

import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;

public class BurnerTile extends GameTile {
    public Orientation orientation;
    public BurnerTile(Orientation orientation) {
        this.orientation = orientation;
    }
    public void init(GameLevel level, int x, int y) {
        if (orientation == Orientation.UP) EntityType.FLAME_UP.spawn(level, x * 100, y * 100 - 300);
        if (orientation == Orientation.LEFT) EntityType.FLAME_LEFT.spawn(level, x * 100 - 300, y * 100);
        if (orientation == Orientation.DOWN) EntityType.FLAME_DOWN.spawn(level, x * 100, y * 100 + 100);
        if (orientation == Orientation.RIGHT) EntityType.FLAME_RIGHT.spawn(level, x * 100 + 100, y * 100);
    }
    public enum Orientation {
        UP,
        LEFT,
        DOWN,
        RIGHT
    }
}
