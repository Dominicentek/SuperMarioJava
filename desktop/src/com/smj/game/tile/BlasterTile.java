package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.FlyingBehavior;
import com.smj.util.AudioPlayer;
import com.smj.util.RNG;

import java.util.ArrayList;

public class BlasterTile extends GameTile {
    public void update(GameLevel level, int x, int y) {
        if (RNG.chance(0.005f)) {
            int mx = Game.player.getPhysics().getHitbox().x;
            int my = Game.player.getPhysics().getHitbox().y;
            int X = x * 100 + 50;
            int Y = y * 100 + 50;
            if (Math.sqrt(Math.pow(mx - X, 2) + Math.pow(my - Y, 2)) < 300) return;
            float speedFactor = 1f;
            if (mx > X) speedFactor *= -1;
            GameEntity bullet = EntityType.BULLET_BILL.spawn(level, x * 100, y * 100);
            bullet.getProperties().drawInBG = true;
            AudioPlayer.EXPLOSION.play(Location.tile(x, y, level));
            bullet.getBehavior(FlyingBehavior.class).speed *= speedFactor;
        }
    }
}
