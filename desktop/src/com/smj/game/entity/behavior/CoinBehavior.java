package com.smj.game.entity.behavior;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.GameEntity;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class CoinBehavior implements EntityBehavior {
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.getPhysics().getConfig().collisionEnabled = false;
        entity.getPhysics().setSpeedY(-entity.getPhysics().getConfig().jumpingSpeed);
    }
    public void update(GameEntity entity, GameLevel level) {
        if (entity.getPhysics().getSpeedY() >= 0) {
            level.getEntityManager().unloadEntity(entity);
            Game.addCoins(1);
            AudioPlayer.COIN.play(Location.entity(entity));
            Game.awardScore(StaticScore.COIN, Location.entity(entity));
        }
    }
}
