package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.FinalFightBrickParticle;
import com.smj.util.AudioPlayer;
import com.smj.util.RNG;

public class FinalFightSwitchBehavior implements EntityBehavior {
    public int timeout = 30;
    public boolean pressed = false;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void update(GameEntity entity, GameLevel level) {
        if (!pressed) return;
        if (timeout > 0) {
            timeout--;
            if (timeout == 0) {
                Game.finalFightSwitchesPressed++;
                for (int i = 0; i < 3; i++) {
                    AudioPlayer.BRICK.play();
                }
                level.camera.screenshake(32, 120);
                for (int i = 0; i < 100; i++) {
                    int x = RNG.range(4, 380);
                    int y = RNG.range(-252, -4);
                    Game.particles.add(new FinalFightBrickParticle(x, y));
                }
            }
        }
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType != EntityType.PLAYER) return;
        if (pressed) return;
        if (collider.getPhysics().getSpeedY() > 0) {
            AudioPlayer.EXPLOSION.play(Location.entity(entity));
            pressed = true;
        }
    }
}
