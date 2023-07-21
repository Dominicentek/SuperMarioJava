package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FinalFightBrickParticle;
import com.smj.util.AudioPlayer;
import com.smj.util.RNG;

public class BigBowserFireBehavior implements EntityBehavior {
    public float scale = 1;
    public int timeout = 120;
    public Integer targetPosX = null;
    public Integer targetPosY = null;
    public Integer sourcePosX = null;
    public Integer sourcePosY = null;
    public int currentPosX = 0;
    public int currentPosY = 0;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.immuneToDeathBarrier = true;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (timeout > 0) timeout--;
        if (timeout == 0) {
            level.getEntityManager().entities.remove(entity);
            level.camera.screenshake(8, 30);
            for (int i = 0; i < 25; i++) {
                int x = RNG.range(4, 380);
                int y = RNG.range(-252, -4);
                Game.particles.add(new FinalFightBrickParticle(x, y));
            }
            AudioPlayer.EXPLOSION.play();
        }
        entity.getProperties().drawInBG = timeout > 60;
        if (sourcePosX == null) sourcePosX = entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2;
        if (sourcePosY == null) sourcePosY = entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2;
        if (targetPosX == null) targetPosX = Game.player.getPhysics().getHitbox().x + Game.player.getPhysics().getHitbox().width / 2;
        if (targetPosY == null) targetPosY = Game.player.getPhysics().getHitbox().y + Game.player.getPhysics().getHitbox().height / 2;
        currentPosX = (int)(Math.pow(1.5 - timeout / 90f, 2) * (targetPosX - sourcePosX)) + sourcePosX;
        currentPosY = (int)(Math.pow(1.5 - timeout / 90f, 2) * (targetPosY - sourcePosY)) + sourcePosY;
        scale = (float)(Math.pow(1 - timeout / 120f, 2) * 4 + 1);
        entity.getPhysics().getHitbox().width = (int)(100 * scale);
        entity.getPhysics().getHitbox().height = (int)(100 * scale);
        entity.getPhysics().getHitbox().x = currentPosX - entity.getPhysics().getHitbox().width / 2;
        entity.getPhysics().getHitbox().y = currentPosY - entity.getPhysics().getHitbox().height / 2;
        entity.getPhysics().getCollideBoxBounds().x = (int)(25 * scale);
        entity.getPhysics().getCollideBoxBounds().y = (int)(25 * scale);
        entity.getPhysics().getCollideBoxBounds().width = (int)(50 * scale);
        entity.getPhysics().getCollideBoxBounds().height = (int)(50 * scale);
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType != EntityType.PLAYER || timeout > 60) return;
        Game.damagePlayer();
    }
}
