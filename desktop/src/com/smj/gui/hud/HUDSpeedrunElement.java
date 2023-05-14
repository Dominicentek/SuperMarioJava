package com.smj.gui.hud;

import com.smj.gui.font.Font;
import com.smj.util.Renderer;

import java.awt.Dimension;

public class HUDSpeedrunElement extends HUDElement {
    public int frames;
    public void render(Renderer renderer) {
        renderer.drawString(getText(), 0, 0);
    }
    public String getText() {
        int hundreds = (int)(frames / 60.0 * 1000);
        int seconds = frames / 60;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        return hours + ":" + String.format("%1$2s", minutes).replaceAll(" ", "0") + ":" + String.format("%1$2s", seconds).replaceAll(" ", "0") + "." + String.format("%1$2s", hundreds).replaceAll(" ", "0");
    }
}
