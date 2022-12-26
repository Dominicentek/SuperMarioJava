package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

import java.awt.Point;

public class PiranhaPlantBehavior implements EntityBehavior {
    public int timeout = 50;
    public int stage = 0;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getProperties().drawInBG = true;
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.spawnOffsetX = 50;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (timeout > 0) {
            timeout--;
            if (stage == 1) entity.getPhysics().getHitbox().y += 4;
            if (stage == 3) entity.getPhysics().getHitbox().y -= 4;
        }
        if (timeout == 0) {
            if (stage == 2) {
                int centerX = entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2;
                int centerY = entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2;
                int playerCenterX = Game.player.getPhysics().getHitbox().x + Game.player.getPhysics().getHitbox().width / 2;
                int playerCenterY = Game.player.getPhysics().getHitbox().y + Game.player.getPhysics().getHitbox().height / 2;
                double distance = Math.sqrt(Math.pow(playerCenterX - centerX, 2) + Math.pow(playerCenterY - centerY, 2));
                if (distance < 400) return;
            }
            stage++;
            if (stage == 4) stage = 0;
            timeout = 50;
        }
    }
}
