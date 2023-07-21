package com.smj.game.tile;

import com.badlogic.gdx.Audio;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.ShellBehavior;
import com.smj.util.AudioPlayer;

public class ContainerTile extends GameTile {
    private EntityType entity;
    private boolean progressive;
    private boolean setEmptyBlockBeforeBump;
    public ContainerTile(EntityType entity, boolean progressive, boolean setEmptyBlockBeforeBump) {
        this.entity = entity;
        this.progressive = progressive;
        this.setEmptyBlockBeforeBump = setEmptyBlockBeforeBump;
    }
    public void onTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        onTouchLeft(entity, level, x, y);
    }
    public void onTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        ShellBehavior behavior = entity.getBehavior(ShellBehavior.class);
        if (behavior == null) return;
        if (behavior.speedFactor != 0) hit(level, x, y);
    }
    public void onTouchBottom(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (entity.getPhysics().getSpeedY() > 0) return;
        hit(level, x, y);
    }
    public EntityType getEntity() {
        return progressive && Game.savefile.powerupState == 0 ? EntityType.MUSHROOM : entity;
    }
    public void hit(GameLevel level, int x, int y) {
        Game.addTileReplacement(x, y, Tiles.EMPTY_BLOCK);
        if (setEmptyBlockBeforeBump) level.setTileAt(Tiles.EMPTY_BLOCK, x, y);
        GameTile.bump(level, x, y, () -> {
            EntityType type = getEntity();
            type.spawn(level, x * 100 + 50 - type.getHitbox().width / 2, y * 100 + 50 - type.getHitbox().height / 2);
            level.setTileAt(Tiles.EMPTY_BLOCK, x, y);
            AudioPlayer.QUESTION_BLOCK.play();
        });
    }
}
