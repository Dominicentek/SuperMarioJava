package com.smj.gui.hud;

import com.smj.util.Renderer;

public class HUDAnimatedCounterElement extends HUDCounterElement {
    public int color = 0xFFFFFF;
    public int timeout = 0;
    public boolean negativeTarget = false;
    public void render(Renderer renderer) {
        int offset = timeout == 59 || timeout == 57 ? 1 : timeout == 58 ? 2 : 0;
        offset *= negativeTarget ? -1 : 1;
        renderer.translate(0, -offset);
        super.render(renderer);
        renderer.translate(0, offset);
    }
    public void update() {
        if (target < value) negativeTarget = true;
        if (target > value) negativeTarget = false;
        super.update();
        if (timeout > 0) timeout--;
    }
    public void valueChanged() {
        timeout = 60;
    }
    public HUDAnimatedCounterElement color(int color) {
        this.color = color;
        return this;
    }
    public int textColor() {
        int color = negativeTarget ? 0xFF0000 : this.color;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        r = (int)((0xFF - r) / 60.0 * (60 - timeout) + r);
        g = (int)((0xFF - g) / 60.0 * (60 - timeout) + g);
        b = (int)((0xFF - b) / 60.0 * (60 - timeout) + b);
        return (r << 16) | (g << 8) | b;
    }
}
