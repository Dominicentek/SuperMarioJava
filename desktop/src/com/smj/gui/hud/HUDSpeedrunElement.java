package com.smj.gui.hud;

import com.smj.gui.font.Font;
import com.smj.util.Renderer;

import java.awt.Dimension;

public class HUDSpeedrunElement extends HUDElement {
    public long beginTime;
    public boolean running = false;
    public void render(Renderer renderer) {
        renderer.drawString(getText(), 0, 0);
    }
    public String getText() {
        long time = running ? System.currentTimeMillis() - beginTime : 0;
        int hundreds = (int)(time / 10) % 100;
        int seconds = (int)(time / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        return hours + ":" + String.format("%1$2s", minutes).replaceAll(" ", "0") + ":" + String.format("%1$2s", seconds).replaceAll(" ", "0") + "." + String.format("%1$2s", hundreds).replaceAll(" ", "0");
    }
    public void start() {
        running = true;
        beginTime = System.currentTimeMillis();
    }
    public void finish() {
        running = false;
    }
}
