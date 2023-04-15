package com.smj.game;

import com.smj.game.fluid.Fluid;
import com.smj.game.tile.GameTile;
import com.smj.game.tile.Tiles;
import com.smj.gui.hud.HUDLayout;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.entity.physics.Physics;
import com.smj.jmario.entity.physics.PhysicsConfig;
import com.smj.jmario.level.Level;
import com.smj.jmario.tile.level.LevelTile;
import com.smj.util.Readable;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.util.ArrayList;
import java.util.Arrays;

public class GameLevel extends Level implements Readable {
    public static final PhysicsConfig PHYSICS_GROUND = new PhysicsConfig().maxJumpHeight(250);
    public static final PhysicsConfig PHYSICS_UNDERWATER = new PhysicsConfig(PHYSICS_GROUND).underwater(true);
    public static final PhysicsConfig PHYSICS_LOW_GRAVITY = new PhysicsConfig(PHYSICS_GROUND).gravity(PHYSICS_GROUND.gravity * 0.25).underwaterGravity(PHYSICS_GROUND.underwaterGravity * 0.25).maxJumpHeight(PHYSICS_GROUND.maxJumpHeight * 3).underwaterSwimSpeed(PHYSICS_GROUND.underwaterSwimSpeed * 3);
    public static boolean onOffTreatment = true;
    public Gimmick gimmick;
    public int music;
    public int tileAnimationTimeout = 10;
    public int time = 0;
    public int spawnX = 0;
    public int spawnY = 0;
    public int id = 0;
    public int theme = 0;
    public boolean[][] loaded;
    public Fluid fluid = null;
    public ArrayList<Warp> warps = new ArrayList<>();
    public GameLevel(ObjectElement element) {
        super(Short.toUnsignedInt(element.getShort("width")), Short.toUnsignedInt(element.getShort("height")));
        music = Byte.toUnsignedInt(element.getByte("music"));
        theme = Byte.toUnsignedInt(element.getByte("theme"));
        Theme theme = Game.THEMES[this.theme];
        gimmick = Gimmick.values()[element.getByte("gimmick")];
        time = Short.toUnsignedInt(element.getShort("time"));
        spawnX = Short.toUnsignedInt(element.getShort("spawnX"));
        spawnY = Short.toUnsignedInt(element.getShort("spawnY"));
        spawnY = Short.toUnsignedInt(element.getShort("spawnY"));
        ListElement warps = element.getList("warps");
        for (int i = 0; i < warps.size(); i++) {
            this.warps.add(Readable.read(warps.getObject(i), Warp.class));
        }
        if (element.contains("fluid")) {
            fluid = Readable.read(element.getObject("fluid"), Fluid.class);
        }
        assignTileList(theme.tileList);
        loaded = new boolean[getLevelBoundaries().width][getLevelBoundaries().height];
        ListElement rows = element.getList("tileData");
        int keycoinAmount = 0;
        for (int y = 0; y < rows.size(); y++) {
            ListElement row = rows.getList(y);
            for (int x = 0; x < row.size(); x++) {
                int tile = Byte.toUnsignedInt(row.getByte(x));
                setTileAt(tile, x, y);
                if (tile == Tiles.ON_OFF_SWITCH_BLUE && Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_SWITCH_RED, x, y);
                if (tile == Tiles.ON_OFF_BLOCK_RED_OFF && Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_BLOCK_RED_ON, x, y);
                if (tile == Tiles.ON_OFF_BLOCK_BLUE_ON && Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_BLOCK_BLUE_OFF, x, y);
                if (tile == Tiles.ON_OFF_SWITCH_RED && !Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_SWITCH_BLUE, x, y);
                if (tile == Tiles.ON_OFF_BLOCK_RED_ON && !Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_BLOCK_RED_OFF, x, y);
                if (tile == Tiles.ON_OFF_BLOCK_BLUE_OFF && !Game.onOff && onOffTreatment) setTileAt(Tiles.ON_OFF_BLOCK_BLUE_ON, x, y);
                if (tile == Tiles.KEY_COIN) keycoinAmount++;
            }
        }
        HUDLayout.KEY_COIN_COUNTER.attachment.max = keycoinAmount;
        HUDLayout.KEY_COIN_COUNTER.visible = keycoinAmount > 0;
        assignBackground(theme.background);
    }
    public void update() {
        for (Entity entity : Arrays.asList(getEntityManager().array())) {
            entity.update(this);
            Physics physics = entity.getPhysics();
            if (!physics.begun()) physics.begin(this);
            physics.advance();
        }
        tileAnimationTimeout--;
        if (tileAnimationTimeout == 0) {
            tileAnimationTimeout = 10;
            for (LevelTile tile : getTileList()) {
                if (tile instanceof GameTile) {
                    GameTile t = (GameTile)tile;
                    t.cycleTexture();
                }
            }
        }
        if (fluid != null) fluid.movement.update();
    }
    public PhysicsConfig getPhysicsConfig() {
        return gimmick == Gimmick.UNDERWATER ? PHYSICS_UNDERWATER : gimmick == Gimmick.LOW_GRAVITY ? PHYSICS_LOW_GRAVITY : PHYSICS_GROUND;
    }
    public Warp getWarpAt(int x, int y) {
        for (Warp warp : warps) {
            if (warp.x == x && warp.y == y) return warp;
        }
        return new Warp(x, y, 0, 21, 11, false);
    }
    public enum Gimmick {
        NONE,
        CASTLE,
        ENEMY,
        UPSIDE_DOWN,
        SPOTLIGHT,
        WIND,
        SLIPPERY,
        UNDERWATER,
        FOG,
        LOW_GRAVITY,
        METEORITES,
        ACID_RAIN,
    }
}
