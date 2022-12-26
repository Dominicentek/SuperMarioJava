package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.util.AudioPlayer;

public class FlameBehavior implements EntityBehavior {
    public int burnTimeout = 120;
    public boolean fire = false;
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().getConfig().gravity = 0;
        entity.getPhysics().getConfig().underwaterGravity = 0;
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER && fire && Game.invincibilityTimeout == 0) {
            Game.damagePlayer();
        }
    }
    public void update(GameEntity entity, GameLevel level) {
        burnTimeout--;
        if (burnTimeout == 0) {
            burnTimeout = 120;
            fire = !fire;
            if (fire) AudioPlayer.BURNER.play(Location.entity(entity));
        }
    }
}
