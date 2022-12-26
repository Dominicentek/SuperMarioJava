package com.smj.gui.hud;

import com.smj.util.Renderer;

public class HUDKeyCounterElement extends HUDAnimatedCounterElement {
    public int max = 0;
    public void render(Renderer renderer) {
        super.render(renderer);
        renderer.drawString("/" + String.format("%1$" + digits + "s", max + "").replaceAll(" ", "0"), digits * 8, 0);
    }
}
