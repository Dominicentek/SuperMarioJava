package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;

public class MushroomContainerTile extends ContainerTile {
    public MushroomContainerTile(boolean setEmptyBlockBeforeBump) {
        super(EntityType.MUSHROOM, false, setEmptyBlockBeforeBump);
    }
    public EntityType getEntity() {
        if (Game.savefile.powerupState == 0) return EntityType.MUSHROOM;
        return EntityType.COIN;
    }
}
