package com.smj.gui.hud;

import com.smj.util.Renderer;

import java.awt.Dimension;

public class HUDLabelledCounterElement extends HUDCounterElement {
    public final String label;
    public HUDLabelledCounterElement(String label) {
        this.label = label;
    }
    public void render(Renderer renderer) {
        renderer.drawString("$c" + String.format("%1$6s", "" + Integer.toHexString(textColor() & 0xFFFFFF).toUpperCase()).replaceAll(" ", "0") + label + ": " + String.format("%1$" + digits + "s", value + "").replaceAll(" ", "0"), 0, 0);
    }
}
