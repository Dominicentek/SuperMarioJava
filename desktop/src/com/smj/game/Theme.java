package com.smj.game;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.EntityType;
import com.smj.game.tile.*;
import com.smj.jmario.level.LevelBackground;
import com.smj.jmario.tile.level.LevelTile;
import com.smj.jmario.tile.level.LevelTileList;

public class Theme {
    public LevelTileList tileList = new LevelTileList();
    public LevelBackground background;
    public Theme(Texture tileset, LevelBackground background) {
        this.background = background;
        for (int i = 1; i < 256; i++) {
            tileList.add(tileList.get(0));
        }
        addTile(Tiles.GROUND, new GameTile().setSolid(true).setDecorable(true), tileset, 0x01);
        addTile(Tiles.BARRIER, new GameTile().setSolid(true), tileset, 0x00);
        addTile(Tiles.STONE_BLOCK, new GameTile().setSolid(true), tileset, 0x02);
        addTile(Tiles.ICE, new MeltableTile(Tiles.AIR).setSolid(true).setAccelerationFactor(0.75f), tileset, 0x04);
        addTile(Tiles.SPIKE, new HarmfulTile().setSolid(true), tileset, 0x05);
        addTile(Tiles.EMPTY_BLOCK, new GameTile().setSolid(true), tileset, 0x06);
        addTile(Tiles.FIREBAR, new FirebarTile().setSolid(true), tileset, 0x07);
        addTile(Tiles.BIG_STONE_TOP_LEFT, new GameTile().setSolid(true), tileset, 0x9D);
        addTile(Tiles.BIG_STONE_TOP_CENTER, new GameTile().setSolid(true), tileset, 0x9E);
        addTile(Tiles.BIG_STONE_TOP_RIGHT, new GameTile().setSolid(true), tileset, 0x9F);
        addTile(Tiles.BIG_STONE_CENTER_LEFT, new GameTile().setSolid(true), tileset, 0xAD);
        addTile(Tiles.BIG_STONE_CENTER_CENTER, new GameTile().setSolid(true), tileset, 0xAE);
        addTile(Tiles.BIG_STONE_CENTER_RIGHT, new GameTile().setSolid(true), tileset, 0xAF);
        addTile(Tiles.BIG_STONE_BOTTOM_LEFT, new GameTile().setSolid(true), tileset, 0xBD);
        addTile(Tiles.BIG_STONE_BOTTOM_CENTER, new GameTile().setSolid(true), tileset, 0xBE);
        addTile(Tiles.BIG_STONE_BOTTOM_RIGHT, new GameTile().setSolid(true), tileset, 0xBF);
        addTile(Tiles.COIN, new CoinTile(), tileset, 0x08, 0x08, 0x08, 0x20, 0x21, 0x20);
        addTile(Tiles.KEY_COIN, new KeyCoinTile(), tileset, 0x09, 0x23, 0x24, 0x25);
        addTile(Tiles.FROZEN_COIN, new MeltableTile(Tiles.COIN).setSolid(true), tileset, 0x0A, 0x0A, 0x0A, 0x26, 0x27, 0x26);
        addTile(Tiles.KEY, new KeyTile(), tileset, 0x1D, 0x1E, 0x1F, 0x1E);
        addTile(Tiles.QUESTION_BLOCK_COIN, new ContainerTile(EntityType.COIN, false).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_MUSHROOM, new MushroomContainerTile().setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_FIRE_FLOWER, new ContainerTile(EntityType.FIRE_FLOWER, true).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_ICE_FLOWER, new ContainerTile(EntityType.ICE_FLOWER, true).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_JUMP_SHOES, new ContainerTile(EntityType.JUMP_SHOES, true).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_SPEED_SHOES, new ContainerTile(EntityType.SPEED_SHOES, true).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_1UP, new ContainerTile(EntityType.LIFE_MUSHROOM, false).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_POISON_MUSHROOM, new ContainerTile(EntityType.POISONOUS_MUSHROOM, false).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.QUESTION_BLOCK_SUPERSTAR, new ContainerTile(EntityType.SUPERSTAR, false).setSolid(true), tileset, 0x0B, 0x0B, 0x0B, 0x2E, 0x2F, 0x2E);
        addTile(Tiles.BRICK_EMPTY, new BrickTile(true).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_COIN, new ContainerTile(EntityType.COIN, false).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_MUSHROOM, new MushroomContainerTile().setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_FIRE_FLOWER, new ContainerTile(EntityType.FIRE_FLOWER, true).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_ICE_FLOWER, new ContainerTile(EntityType.ICE_FLOWER, true).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_JUMP_SHOES, new ContainerTile(EntityType.JUMP_SHOES, true).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_SPEED_SHOES, new ContainerTile(EntityType.SPEED_SHOES, true).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_1UP, new ContainerTile(EntityType.LIFE_MUSHROOM, false).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_POISON_MUSHROOM, new ContainerTile(EntityType.POISONOUS_MUSHROOM, false).setSolid(true), tileset, 0x0C);
        addTile(Tiles.BRICK_SUPERSTAR, new ContainerTile(EntityType.SUPERSTAR, false).setSolid(true), tileset, 0x0C);
        addTile(Tiles.FRAGILE_BRICK, new BrickTile(false).setSolid(true), tileset, 0x0C);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_COIN, new ContainerTile(EntityType.COIN, false), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_MUSHROOM, new MushroomContainerTile(), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_FIRE_FLOWER, new ContainerTile(EntityType.FIRE_FLOWER, true), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_ICE_FLOWER, new ContainerTile(EntityType.ICE_FLOWER, true), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_JUMP_SHOES, new ContainerTile(EntityType.JUMP_SHOES, true), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_SPEED_SHOES, new ContainerTile(EntityType.SPEED_SHOES, true), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_1UP, new ContainerTile(EntityType.LIFE_MUSHROOM, false), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_POISON_MUSHROOM, new ContainerTile(EntityType.POISONOUS_MUSHROOM, false), tileset, 0x00);
        addTile(Tiles.INVISIBLE_QUESTION_BLOCK_SUPERSTAR, new ContainerTile(EntityType.SUPERSTAR, false), tileset, 0x00);
        addTile(Tiles.PIPE_VERTICAL_TOP_LEFT, new GameTile().setSolid(true), tileset, 0x13);
        addTile(Tiles.PIPE_VERTICAL_TOP_RIGHT, new GameTile().setSolid(true), tileset, 0x14);
        addTile(Tiles.PIPE_VERTICAL_BOTTOM_LEFT, new GameTile().setSolid(true), tileset, 0x15);
        addTile(Tiles.PIPE_VERTICAL_BOTTOM_RIGHT, new GameTile().setSolid(true), tileset, 0x16);
        addTile(Tiles.PIPE_HORIZONTAL_TOP_LEFT, new GameTile().setSolid(true), tileset, 0x9C);
        addTile(Tiles.PIPE_HORIZONTAL_TOP_RIGHT, new GameTile().setSolid(true), tileset, 0xAC);
        addTile(Tiles.PIPE_HORIZONTAL_BOTTOM_LEFT, new GameTile().setSolid(true), tileset, 0x9B);
        addTile(Tiles.PIPE_HORIZONTAL_BOTTOM_RIGHT, new GameTile().setSolid(true), tileset, 0xAB);
        addTile(Tiles.WARPABLE_PIPE, new WarpablePipeTile().setSolid(true), tileset, 0x13);
        addTile(Tiles.ON_OFF_SWITCH_RED, new RedOnOffSwitchTile().setSolid(true), tileset, 0x1B, 0x1B, 0x28, 0x28);
        addTile(Tiles.ON_OFF_SWITCH_BLUE, new BlueOnOffSwitchTile().setSolid(true), tileset, 0x1C, 0x1C, 0x29, 0x29);
        addTile(Tiles.ON_OFF_BLOCK_RED_OFF, new GameTile(), tileset, 0x19);
        addTile(Tiles.ON_OFF_BLOCK_BLUE_OFF, new GameTile(), tileset, 0x1A);
        addTile(Tiles.ON_OFF_BLOCK_RED_ON, new GameTile().setSolid(true), tileset, 0x17);
        addTile(Tiles.ON_OFF_BLOCK_BLUE_ON, new GameTile().setSolid(true), tileset, 0x18);
        addTile(Tiles.BURNER_UP, new BurnerTile(BurnerTile.Orientation.UP).setSolid(true), tileset, 0x2A);
        addTile(Tiles.BURNER_LEFT, new BurnerTile(BurnerTile.Orientation.LEFT).setSolid(true), tileset, 0x2B);
        addTile(Tiles.BURNER_DOWN, new BurnerTile(BurnerTile.Orientation.DOWN).setSolid(true), tileset, 0x2C);
        addTile(Tiles.BURNER_RIGHT, new BurnerTile(BurnerTile.Orientation.RIGHT).setSolid(true), tileset, 0x2D);
        addTile(Tiles.BIG_COIN_TOP_LEFT_10, new BigCoinTile(10, 1000, Corner.TOP_LEFT), tileset, 0x30, 0x32, 0x34, 0x36);
        addTile(Tiles.BIG_COIN_TOP_RIGHT_10, new BigCoinTile(10, 1000, Corner.TOP_RIGHT), tileset, 0x31, 0x33, 0x35, 0x37);
        addTile(Tiles.BIG_COIN_BOTTOM_LEFT_10, new BigCoinTile(10, 1000, Corner.BOTTOM_LEFT), tileset, 0x40, 0x42, 0x44, 0x46);
        addTile(Tiles.BIG_COIN_BOTTOM_RIGHT_10, new BigCoinTile(10, 1000, Corner.BOTTOM_RIGHT), tileset, 0x41, 0x43, 0x45, 0x47);
        addTile(Tiles.BIG_COIN_TOP_LEFT_30, new BigCoinTile(30, 3000, Corner.TOP_LEFT), tileset, 0x38, 0x3A, 0x3C, 0x3E);
        addTile(Tiles.BIG_COIN_TOP_RIGHT_30, new BigCoinTile(30, 3000, Corner.TOP_RIGHT), tileset, 0x39, 0x3B, 0x3D, 0x3F);
        addTile(Tiles.BIG_COIN_BOTTOM_LEFT_30, new BigCoinTile(30, 3000, Corner.BOTTOM_LEFT), tileset, 0x48, 0x4A, 0x4C, 0x4E);
        addTile(Tiles.BIG_COIN_BOTTOM_RIGHT_30, new BigCoinTile(30, 3000, Corner.BOTTOM_RIGHT), tileset, 0x49, 0x4B, 0x4D, 0x4F);
        addTile(Tiles.BIG_COIN_TOP_LEFT_50, new BigCoinTile(50, 5000, Corner.TOP_LEFT), tileset, 0x50, 0x52, 0x54, 0x56);
        addTile(Tiles.BIG_COIN_TOP_RIGHT_50, new BigCoinTile(50, 5000, Corner.TOP_RIGHT), tileset, 0x51, 0x53, 0x55, 0x57);
        addTile(Tiles.BIG_COIN_BOTTOM_LEFT_50, new BigCoinTile(50, 5000, Corner.BOTTOM_LEFT), tileset, 0x60, 0x62, 0x64, 0x66);
        addTile(Tiles.BIG_COIN_BOTTOM_RIGHT_50, new BigCoinTile(50, 5000, Corner.BOTTOM_RIGHT), tileset, 0x61, 0x63, 0x65, 0x67);
        addTile(Tiles.CHECKPOINT_TOP_LEFT, new CheckpointTile(Corner.TOP_LEFT), tileset, 0x58);
        addTile(Tiles.CHECKPOINT_TOP_RIGHT, new CheckpointTile(Corner.TOP_RIGHT), tileset, 0x59);
        addTile(Tiles.CHECKPOINT_BOTTOM_LEFT, new CheckpointTile(Corner.BOTTOM_LEFT), tileset, 0x68);
        addTile(Tiles.CHECKPOINT_BOTTOM_RIGHT, new CheckpointTile(Corner.BOTTOM_RIGHT), tileset, 0x69);
        addTile(Tiles.COLLECTED_CHECKPOINT_TOP_LEFT, new GameTile(), tileset, 0x5A);
        addTile(Tiles.COLLECTED_CHECKPOINT_TOP_RIGHT, new GameTile(), tileset, 0x5B);
        addTile(Tiles.COLLECTED_CHECKPOINT_BOTTOM_LEFT, new GameTile(), tileset, 0x6A);
        addTile(Tiles.COLLECTED_CHECKPOINT_BOTTOM_RIGHT, new GameTile(), tileset, 0x6B);
        addTile(Tiles.CLOCK_10S, new TimeTile(10), tileset, 0x5C);
        addTile(Tiles.CLOCK_30S, new TimeTile(30), tileset, 0x5D);
        addTile(Tiles.CLOCK_50S, new TimeTile(50), tileset, 0x5E);
        addTile(Tiles.CLOCK_100S, new TimeTile(100), tileset, 0x5F);
        addTile(Tiles.ENEMY_GOOMBA, new EntitySpawnerTile(EntityType.GOOMBA), tileset, 0x00);
        addTile(Tiles.ENEMY_GREEN_KOOPA, new EntitySpawnerTile(EntityType.GREEN_KOOPA), tileset, 0x00);
        addTile(Tiles.ENEMY_RED_KOOPA, new EntitySpawnerTile(EntityType.RED_KOOPA), tileset, 0x00);
        addTile(Tiles.ENEMY_SPINY, new EntitySpawnerTile(EntityType.SPINY), tileset, 0x00);
        addTile(Tiles.ENEMY_BUZZY_BEETLE, new EntitySpawnerTile(EntityType.BUZZY_BEETLE), tileset, 0x00);
        addTile(Tiles.ENEMY_PIRANHA_PLANT, new EntitySpawnerTile(EntityType.PIRANHA_PLANT), tileset, 0x00);
        addTile(Tiles.ENEMY_BULLET_BILL, new EntitySpawnerTile(EntityType.BULLET_BILL), tileset, 0x00);
        addTile(Tiles.ENEMY_CHEEP_CHEEP, new EntitySpawnerTile(EntityType.CHEEP_CHEEP), tileset, 0x00);
        addTile(Tiles.ENEMY_BLOOPER, new EntitySpawnerTile(EntityType.BLOOPER), tileset, 0x00);
        addTile(Tiles.ENEMY_PODOBOO, new EntitySpawnerTile(EntityType.PODOBOO), tileset, 0x00);
        /* Unused Enemies (Maybe will be added soon?)
        addTile(Tiles.ENEMY_GREEN_PARAKOOPA, new GameTile(), tileset, 0x00);
        addTile(Tiles.ENEMY_RED_PARAKOOPA, new GameTile(), tileset, 0x00);
        addTile(Tiles.ENEMY_LAKITU, new GameTile(), tileset, 0x00);
        addTile(Tiles.ENEMY_HAMMER_BRO, new GameTile(), tileset, 0x00);
        */
        addTile(Tiles.STAR, new StarTile(), tileset, 0x6C, 0x6D, 0x6E, 0x6D);
        addTile(Tiles.LOCKED_STAR, new GameTile(), tileset, 0x6F);
        addTile(Tiles.BULLET_BILL_LAUNCHER_TOP, new BlasterTile().setSolid(true), tileset, 0x70);
        addTile(Tiles.BULLET_BILL_LAUNCHER_MIDDLE, new GameTile().setSolid(true), tileset, 0x71);
        addTile(Tiles.BULLET_BILL_LAUNCHER_BOTTOM, new GameTile().setSolid(true), tileset, 0x72);
        addTile(Tiles.EXCLAMATION_POINT_CIRCLE_BALL_THING_IDK_HOW_TO_CALL_THIS, new ExclamationPointCircleBallThingIdkHowToCallThisTile(), tileset, 0x22);
        addTile(Tiles.PUSHABLE_STONE, new EntitySpawnerTile(EntityType.PUSHABLE_STONE), tileset, 0x00);
        addTile(Tiles.ICICLE, new EntitySpawnerTile(EntityType.ICICLE), tileset, 0x00);
        addTile(Tiles.UPWARDS_STREAM, new EntitySpawnerTile(EntityType.UP_PIPE_STREAM), tileset, 0x00);
        addTile(Tiles.LEFT_STREAM, new EntitySpawnerTile(EntityType.LEFT_PIPE_STREAM), tileset, 0x00);
        addTile(Tiles.DOWNWARDS_STREAM, new EntitySpawnerTile(EntityType.DOWN_PIPE_STREAM), tileset, 0x00);
        addTile(Tiles.RIGHT_STREAM, new EntitySpawnerTile(EntityType.RIGHT_PIPE_STREAM), tileset, 0x00);
    }
    private void addTile(int id, LevelTile tile, Texture tileset, Integer... locations) {
        tileList.set(id, ((GameTile)tile).setTextureLocations(locations).setTexture(tileset));
    }
}