package com.smj.gui.hud;

import com.smj.util.Renderer;

import java.awt.Dimension;

public class HUDCounterElement extends HUDElement {
    public int digits = 1;
    public int target;
    public int value;
    public int step = 1;
    public boolean limited = false;
    public void render(Renderer renderer) {
        renderer.drawString("$c" + String.format("%1$6s", "" + Integer.toHexString(textColor() & 0xFFFFFF).toUpperCase()).replaceAll(" ", "0") + String.format("%1$" + digits + "s", value + "").replaceAll(" ", "0"), 0, 0);
    }
    public void update() {
        if (step <= 0 && value != target) {
            value = target;
            valueChanged();
        }
        else {
            if (target > value) {
                value += step;
                if (target < value) value = target;
                valueChanged();
            }
            if (target < value) {
                value -= step;
                if (target > value) value = target;
                valueChanged();
            }
        }
        if (limited && value >= Math.pow(10, digits)) value = (int)Math.pow(10, digits) - 1;
    }
    public void valueChanged() {}
    public int textColor() {
        return 0xFFFFFF;
    }
    public void set(int value) {
        target = value;
        this.value = value;
    }
    public HUDCounterElement digits(int digits) {
        this.digits = digits;
        return this;
    }
    public HUDCounterElement step(int step) {
        this.step = step;
        return this;
    }
    public HUDCounterElement limit() {
        limited = true;
        return this;
    }
}
