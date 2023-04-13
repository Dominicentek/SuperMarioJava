package com.smj.game.entity.behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.Location;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.options.Controls;
import com.smj.game.particle.BubbleParticle;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.AudioPlayer;

public class PlayerBehavior implements EntityBehavior {
    public boolean facingLeft = false;
    public int jumpTimer = -1;
    public int bubbleTimeout = 60;
    public void update(GameEntity entity, GameLevel level) {
        if (Game.consoleOpen) {
            entity.getPhysics().getMovement().setWalkingLeft(false);
            entity.getPhysics().getMovement().setWalkingRight(false);
            entity.getPhysics().getMovement().setJumping(false);
            entity.getPhysics().getMovement().setRunning(false);
            return;
        }
        if (Controls.LEFT.isPressed()) facingLeft = true;
        if (Controls.RIGHT.isPressed()) facingLeft = false;
        entity.getPhysics().getMovement().setWalkingLeft(Controls.LEFT.isPressed());
        entity.getPhysics().getMovement().setWalkingRight(Controls.RIGHT.isPressed());
        entity.getPhysics().getMovement().setJumping(Controls.JUMP.isPressed());
        entity.getPhysics().getMovement().setRunning(Controls.RUN.isPressed());
        if (Game.isCrouching && !entity.getPhysics().isInAir()) {
            entity.getPhysics().getMovement().setWalkingLeft(false);
            entity.getPhysics().getMovement().setWalkingRight(false);
        }
        if (!entity.getPhysics().isInAir() && Game.invincibilityTimeout == 0) entity.score.reset();
        if (entity.getPhysics().getHitbox().y >= entity.getLevel().getLevelBoundaries().height * 100 + 50) Game.die();
        if (Controls.RUN.isJustPressed()) {
            if (Game.savefile.powerupState == 2) {
                GameEntity fireball = EntityType.FIREBALL.spawn(level, entity.getPhysics().getHitbox().x + (facingLeft ? -entity.getPhysics().getHitbox().width + 50 : entity.getPhysics().getHitbox().width), entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2);
                if (!facingLeft) fireball.getBehavior(WalkingBehavior.class).speedFactor = 0.5f;
                AudioPlayer.FIREBALL.play(Location.entity(entity));
            }
            if (Game.savefile.powerupState == 3) {
                GameEntity iceball = EntityType.ICEBALL.spawn(level, entity.getPhysics().getHitbox().x + (facingLeft ? -entity.getPhysics().getHitbox().width + 50 : entity.getPhysics().getHitbox().width), entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2);
                if (!facingLeft) iceball.getBehavior(WalkingBehavior.class).speedFactor = 0.5f;
                AudioPlayer.FIREBALL.play(Location.entity(entity));
            }
        }
        if (!HUDLayout.SPEEDRUN_TIMER.running) HUDLayout.SPEEDRUN_TIMER.start();
        if (jumpTimer >= 0) jumpTimer--;
        bubbleTimeout--;
        if (bubbleTimeout == 0) {
            bubbleTimeout = 60;
            int x = entity.getPhysics().getHitbox().x + (facingLeft ? 0 : entity.getPhysics().getHitbox().width);
            int y = entity.getPhysics().getHitbox().y;
            x = x * 16 / 100;
            y = y * 16 / 100;
            if (level.gimmick == GameLevel.Gimmick.UNDERWATER) Game.particles.add(new BubbleParticle(x, y, 0, -0.5));
        }
    }
    public void onTileTouchUp(GameEntity entity, GameLevel level, int x, int y) {
        if (!level.getTileList().get(level.getTileAt(x, y)).isSolid()) return;
        AudioPlayer.BUMP.play(Location.entity(entity));
    }
}
