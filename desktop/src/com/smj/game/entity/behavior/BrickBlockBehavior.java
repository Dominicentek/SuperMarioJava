package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.util.AudioPlayer;

public class BrickBlockBehavior implements EntityBehavior {
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.BOWSER) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    EntityType.BRICK.spawn(level, entity.getPhysics().getHitbox().x + x * 50, entity.getPhysics().getHitbox().y + y * 50);
                }
                AudioPlayer.BRICK.play();
            }
        }
    }
}
