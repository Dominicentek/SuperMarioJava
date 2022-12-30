package com.smj.game.entity.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.smj.game.Game;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.PlayerBehavior;
import com.smj.game.options.Controls;

import java.awt.Rectangle;

public class PlayerTextureProvider extends TextureProvider {
    private static final int STATE_STANDING = 0;
    private static final int STATE_WALKING_1 = 1;
    private static final int STATE_WALKING_2 = 2;
    private static final int STATE_WALKING_3 = 3;
    private static final int STATE_TURNING = 4;
    private static final int STATE_JUMPING = 5;
    private static final int STATE_CROUCHING = 6;
    private static final int STATE_SWIMMING_1 = 7;
    private static final int STATE_SWIMMING_2 = 8;
    private static final int STATE_SWIMMING_UP_1 = 9;
    private static final int STATE_SWIMMING_UP_2 = 10;
    private static final int STATE_SWIMMING_UP_3 = 11;
    private static final int STATE_SWIMMING_UP_4 = 12;
    private int prevX;
    public PlayerTextureProvider(Texture texture) {
        super(texture);
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        int speedX = prevX - entity.getPhysics().getHitbox().x;
        prevX = entity.getPhysics().getHitbox().x;
        int powerup = Game.powerupTimeout == 0 ? Game.savefile.powerupState : (Game.powerupTimeout / 10 % 2 == 1 ? Game.savefile.powerupState : Game.prevState);
        int x;
        int y = powerup == 0 ? 0 : powerup * 32 - 16;
        int width = 16;
        int height = powerup == 0 ? 16 : 32;
        if (Game.isCrouching) x = STATE_CROUCHING;
        else if (entity.getPhysics().getConfig().underwater && entity.getPhysics().isVisuallyInAir()) {
            int jumpTimer = entity.getBehavior(PlayerBehavior.class).jumpTimer;
            int offset = (int)(System.currentTimeMillis() % 500) / 250;
            if (jumpTimer == -1) x = STATE_SWIMMING_1;
            else x = (jumpTimer / 8 * 2) + STATE_SWIMMING_UP_1;
            x += offset;
        }
        else if (entity.getPhysics().isVisuallyInAir()) x = STATE_JUMPING;
        else if (speedX == 0) x = STATE_STANDING;
        else if ((speedX < 0 && flipX) || (speedX > 0 && !flipX)) x = STATE_TURNING;
        else x = entity.getPhysics().getHitbox().x % 150 < 50 ? STATE_WALKING_1 : entity.getPhysics().getHitbox().x % 150 < 100 ? STATE_WALKING_2 : STATE_WALKING_3;
        flipX = entity.getBehavior(PlayerBehavior.class).facingLeft;
        x = MathUtils.clamp(x, 0, STATE_SWIMMING_UP_4);
        x *= 16;
        return new Rectangle(x, y, width, height);
    }
}
