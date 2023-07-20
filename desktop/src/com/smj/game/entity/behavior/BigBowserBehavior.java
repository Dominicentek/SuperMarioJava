package com.smj.game.entity.behavior;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FinalFightBrickParticle;
import com.smj.util.AudioPlayer;
import com.smj.util.RNG;

public class BigBowserBehavior implements EntityBehavior {
    public boolean finished = false;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
        entity.priority = -1;
        entity.immuneToDeathBarrier = true;
    }
    public void update(GameEntity entity, GameLevel level) {
        entity.getPhysics().getHitbox().x = (int)(Game.cameraX * 100 / 16) - entity.getPhysics().getHitbox().width / 2 + 1200;
        if (entity.getPhysics().getConfig().gravity == 0) entity.getPhysics().getHitbox().y = 1600 - entity.getPhysics().getHitbox().height;
        if (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height > 1600 && !finished) {
            entity.getPhysics().getConfig().gravity = 0;
            level.camera.screenshake(16, 60);
            if (!Game.player.getPhysics().inAir) Game.stunTimer = 90;
            for (int i = 0; i < 50; i++) {
                int x = RNG.range(4, 380);
                int y = RNG.range(-252, -4);
                Game.particles.add(new FinalFightBrickParticle(x, y));
            }
            for (int i = 0; i < 3; i++) {
                AudioPlayer.EXPLOSION.play();
            }
        }
        if (Game.finalFightSwitchesPressed == 3 && !finished) {
            finished = true;
            if (entity.getPhysics().getConfig().gravity == 0) {
                entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity / 4;
                entity.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed);
            }
        }
        if (entity.getPhysics().getHitbox().y > 2400) {
            AudioPlayer.EXPLOSION.play();
            Game.playCastleCutscene = true;
            Game.finishLevel();
        }
        if (RNG.chance(0.005f) && entity.getPhysics().getConfig().gravity == 0) {
            if (RNG.chance(0.2f)) {
                entity.getPhysics().getConfig().gravity = level.getPhysicsConfig().gravity / 4;
                entity.getPhysics().setSpeedY(-level.getPhysicsConfig().jumpingSpeed);
            }
            else {
                AudioPlayer.BURNER.play();
                EntityType.BIG_BOWSER_FIRE.spawn(level, entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2 - 50, entity.getPhysics().getHitbox().y + 350);
            }
        }
    }
}
