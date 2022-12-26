package com.smj.gui.hud;

import com.smj.game.Game;

public class HUDTimerElement extends HUDCounterElement {
    public void update() {
        value = Game.time;
    }
    public int textColor() {
        if (value > 100 || Game.finishTimeout != -1) return 0xFFFFFF;
        double sin = (Math.sin(Math.toRadians((Game.timeTimeout + 15) * 6)) + 1) / 2;
        int gb = Game.time <= 10 ? 0x3F + (int)(sin * 0xC0) : 0x7F + (int)(sin * 0x80);
        return 0xFF0000 | (gb << 8) | gb;
    }
}
