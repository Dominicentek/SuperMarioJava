package com.smj.jmario.tile.level;

import com.smj.jmario.entity.Entity;
import com.smj.jmario.level.Level;
import com.smj.jmario.tile.BlankTileModificationException;
import com.smj.jmario.tile.TileList;

import java.awt.image.BufferedImage;

public class LevelTileList extends TileList<LevelTile> {
    public LevelTileList() {
        super();
    }
    public LevelTile createBlankTile() {
        return new LevelTile() {
            public void onTouch(Entity entity, Level level, int x, int y) {}
            public void onTouchTop(Entity entity, Level level, int x, int y) {}
            public void onTouchLeft(Entity entity, Level level, int x, int y) {}
            public void onTouchRight(Entity entity, Level level, int x, int y) {}
            public void onTouchBottom(Entity entity, Level level, int x, int y) {}
            public void init(Level level, int x, int y) {}
            public void update(Level level, int x, int y) {}
            public LevelTile setSolid(boolean solid) {
                throw new BlankTileModificationException();
            }
            public LevelTile setTexture(BufferedImage texture) {
                throw new BlankTileModificationException();
            }
            public LevelTile setDecorable(boolean decorable) {
                throw new BlankTileModificationException();
            }
        };
    }
}
