package com.smj.game.tile;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Warp;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.options.Controls;
import com.smj.util.Transition;

public class WarpablePipeTile extends GameTile {
    public void onTouchTop(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType != EntityType.PLAYER) return;
        if (Game.currentChallenge != null && !Game.currentChallenge.allowWarps) return;
        if (Game.currentLevel.gimmick == GameLevel.Gimmick.UPSIDE_DOWN ? Controls.UP.isPressed() : Controls.DOWN.isPressed()) {
            int minX = x * 100 + 25;
            if (entity.getPhysics().getHitbox().x >= minX) {
                Game.warp(level.getWarpAt(x, y));
            }
        }
    }
}
