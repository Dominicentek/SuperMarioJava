package com.smj.game.entity.texture;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.entity.GameEntity;

import java.awt.Rectangle;

public class AnimatedTextureProvider extends TextureProvider {
    private Rectangle region;
    private int timer = 10;
    private int timeout;
    private int frames;
    private int currentFrame;
    private Texture texture;
    public AnimatedTextureProvider(Texture texture, Integer frames) {
        this(texture, frames, 10);
    }
    public AnimatedTextureProvider(Texture texture, Integer frames, Integer timeout) {
        super(texture);
        this.frames = frames;
        this.texture = texture;
        this.timeout = timeout;
        region = new Rectangle(0, 0, texture.getWidth() / frames, texture.getHeight());
    }
    public Rectangle getTextureRegion(GameEntity entity) {
        timer--;
        if (timer == 0) {
            timer = timeout;
            currentFrame++;
            if (currentFrame == frames) currentFrame = 0;
            region = new Rectangle(texture.getWidth() / frames * currentFrame, 0, texture.getWidth() / frames, texture.getHeight());
        }
        return region;
    }
}
