package com.smj.game;

import com.smj.util.Readable;
import com.smj.util.bjson.ObjectElement;

public class Warp implements Readable {
    public int x;
    public int y;
    public int levelID;
    public int spawnX;
    public int spawnY;
    public boolean goDown;
    public Warp(int x, int y, int levelID, int spawnX, int spawnY, boolean goDown) {
        this.x = x;
        this.y = y;
        this.levelID = levelID;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.goDown = goDown;
    }
    public Warp(ObjectElement element) {
        x = Short.toUnsignedInt(element.getShort("x"));
        y = Short.toUnsignedInt(element.getShort("y"));
        levelID = Byte.toUnsignedInt(element.getByte("levelID"));
        spawnX = Short.toUnsignedInt(element.getShort("spawnX"));
        spawnY = Short.toUnsignedInt(element.getShort("spawnY"));
        goDown = element.getBoolean("goDown");
    }
}
