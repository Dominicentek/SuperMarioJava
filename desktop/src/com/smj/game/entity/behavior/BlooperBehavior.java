package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;

public class BlooperBehavior implements EntityBehavior {
    public int chargeTimeout = 0;
    public void update(GameEntity entity, GameLevel level) {
        if (entity.getPhysics().getSpeedX() != 0) return;
        int x = entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2;
        int y = entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2;
        int px = Game.player.getPhysics().getHitbox().x + Game.player.getPhysics().getHitbox().width / 2;
        int py = Game.player.getPhysics().getHitbox().y + Game.player.getPhysics().getHitbox().height / 2;
        int hdis = Math.abs(x - px);
        double dis = Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));
        if (chargeTimeout > 0) {
            chargeTimeout--;
            if (chargeTimeout == 0) {
                double speed = -1;
                float factor = px < x ? 1 : -1;
                entity.getPhysics().setSpeedY(speed / 1.5);
                entity.getPhysics().setSpeedX(speed * factor);
            }
            return;
        }
        int tx = (entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) / 100;
        int ty = (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height) / 100;
        if (((Game.player.getPhysics().getHitbox().y < entity.getPhysics().getHitbox().y /*|| hdis > 75*/) && dis < 1500) || level.getTileList().get(level.getTileAt(tx, ty + 2)).isSolid()) {
            chargeTimeout = 15;
        }
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().terminalVelocity /= 10;
    }
}
