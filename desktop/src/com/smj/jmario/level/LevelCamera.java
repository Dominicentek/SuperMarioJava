package com.smj.jmario.level;

public class LevelCamera {
    public double targetX;
    public double targetY;
    public double x;
    public double y;
    public boolean locked;
    public void setTarget(double x, double y) {
        if (locked) return;
        targetX = x;
        targetY = y;
    }
    public void update() {
        x += (targetX - x) * 0.1;
        y += (targetY - y) * 0.1;
    }
    public void snap() {
        x = targetX;
        y = targetY;
    }
}
