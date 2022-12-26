package com.smj.gui.hud;

import com.smj.util.Renderer;

import java.awt.Dimension;
import java.awt.Point;

public abstract class HUDElement {
    public boolean visible = true;
    public Point position = new Point(0, 0);
    public abstract void render(Renderer renderer);
    public void update() {}
}
