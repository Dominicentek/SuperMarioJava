package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class FlameTextureProvider extends TextureProvider {
    public int timeout = 60;
    public int animationTimeout = 2;
    public boolean fire = false;
    public FlipState x;
    public FlipState y;
    public int width;
    public int height;
    public FlameTextureProvider(Texture texture, FlipState x, FlipState y) {
        super(texture);
        width = texture.getWidth() / 4;
        height = texture.getHeight();
        this.x = x;
        this.y = y;
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        animationTimeout--;
        if (animationTimeout == 0) {
            animationTimeout = 2;
            timeout--;
            if (timeout == 0) {
                timeout = 60;
                fire = !fire;
            }
        }
        int frame = (60 - timeout) / 2;
        frame = frame == 29 || frame == 0 ? 0 : frame == 28 || frame == 1 ? 1 : 2;
        flipX = x != FlipState.NEVER && (x == FlipState.ALWAYS || timeout % 2 == 1);
        flipY = y != FlipState.NEVER && (y == FlipState.ALWAYS || timeout % 2 == 1);
        return fire ? frame == 0 ? new Rectangle(0, 0, width, height) : frame == 1 ? new Rectangle(width, 0, width, height) : new Rectangle(width * (2 + timeout % 2), 0, width, height) : new Rectangle(0, 0, 1, 1);
    }
    public enum FlipState {
        NEVER,
        ANIMATE,
        ALWAYS
    }
}
