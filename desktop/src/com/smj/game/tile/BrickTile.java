package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.ShellBehavior;
import com.smj.game.particle.SmokeParticle;
import com.smj.game.score.StaticScore;
import com.smj.util.AudioPlayer;

public class BrickTile extends GameTile {
    public boolean hasToBeBig;
    public BrickTile(boolean hasToBeBig) {
        this.hasToBeBig = hasToBeBig;
    }
    public void onTouchBottom(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (Game.savefile.powerupState == 0 && hasToBeBig) return;
        destroy(level, x, y);
        Game.awardScore(StaticScore.BRICK, Location.tile(x, y, level));
        entity.getPhysics().setSpeedY(0);
        entity.getPhysics().jumping = false;
        entity.getPhysics().jumpingUpward = false;
    }
    public void onTouchRight(GameEntity entity, GameLevel level, int x, int y) {
        onTouchLeft(entity, level, x, y);
    }
    public void onTouchLeft(GameEntity entity, GameLevel level, int x, int y) {
        ShellBehavior behavior = entity.getBehavior(ShellBehavior.class);
        if (behavior == null) return;
        if (behavior.speedFactor != 0) destroy(level, x, y);
        behavior.changeDirection();
    }
    public void destroy(GameLevel level, int x, int y) {
        level.setTileAt(Tiles.AIR, x, y);
        Game.particles.add(new SmokeParticle(x * 16 + 8, y * 16 + 8));
        AudioPlayer.BRICK.play();
        EntityType.BRICK.spawn(level, x * 100, y * 100);
        EntityType.BRICK.spawn(level, x * 100 + 50, y * 100);
        EntityType.BRICK.spawn(level, x * 100, y * 100 + 50);
        EntityType.BRICK.spawn(level, x * 100 + 50, y * 100 + 50);
    }
}
