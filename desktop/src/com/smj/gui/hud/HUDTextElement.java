package com.smj.gui.hud;

import com.smj.util.Renderer;

import java.awt.Dimension;

public class HUDTextElement extends HUDElement {
    public String text = "";
    public void render(Renderer renderer) {
        renderer.drawString(text, 0, 0);
    }
}
