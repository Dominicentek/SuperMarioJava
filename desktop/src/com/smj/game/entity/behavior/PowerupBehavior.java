package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class PowerupBehavior implements EntityBehavior {
    public int state;
    public PowerupBehavior(Integer state) {
        this.state = state;
    }
    public void onEntityCollide(GameEntity entity, GameLevel level, GameEntity collider) {
        if (collider.entityType == EntityType.PLAYER) {
            level.getEntityManager().unloadEntity(entity);
            if (Game.savefile.powerupState >= 2 && state == 1) {
                Game.awardScore(StaticScore.POWERUP, Location.entity(entity));
                AudioPlayer.POWERUP.play(Location.entity(entity));
            }
            else Game.setPowerup(state);
        }
    }
}
