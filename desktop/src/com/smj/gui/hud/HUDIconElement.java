package com.smj.gui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

import java.awt.Dimension;

public class HUDIconElement<T extends HUDElement> extends HUDElement {
    public T attachment;
    public Texture texture;
    public static final Texture COIN = TextureLoader.get("images/hud/coin.png");
    public static final Texture MUSHROOM = TextureLoader.get("images/hud/mushroom.png");
    public static final Texture CLOCK = TextureLoader.get("images/hud/clock.png");
    public static final Texture KEY_COIN = TextureLoader.get("images/hud/keycoin.png");
    public static final Texture STAR = TextureLoader.get("images/hud/star.png");
    public HUDIconElement(Texture texture, T attachment) {
        this.attachment = attachment;
        this.texture = texture;
    }
    public void render(Renderer renderer) {
        renderer.draw(texture, 0, 0);
        renderer.translate(texture.getWidth() + 4, 0);
        attachment.render(renderer);
        renderer.translate(-texture.getWidth() - 4, 0);
    }
    public void update() {
        attachment.update();
    }
}
