package com.smj.game.tile;

import com.badlogic.gdx.Audio;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.util.AudioPlayer;

public class ContainerTile extends GameTile {
    private EntityType entity;
    private boolean progressive;
    public ContainerTile(EntityType entity, boolean progressive) {
        this.entity = entity;
        this.progressive = progressive;
    }
    public void onTouchBottom(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (entity.getPhysics().getSpeedY() > 0) return;
        GameTile.bump(level, x, y, () -> {
            EntityType type = getEntity();
            type.spawn(level, x * 100 + 50 - type.getHitbox().width / 2, y * 100 + 50 - type.getHitbox().height / 2);
            level.setTileAt(Tiles.EMPTY_BLOCK, x, y);
            AudioPlayer.QUESTION_BLOCK.play();
        });
    }
    public EntityType getEntity() {
        return progressive && Game.savefile.powerupState == 0 ? EntityType.MUSHROOM : entity;
    }
}
