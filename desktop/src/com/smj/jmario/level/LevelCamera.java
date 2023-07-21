package com.smj.jmario.level;

import com.smj.util.RNG;

public class LevelCamera {
    public double targetX;
    public double targetY;
    public double x;
    public double y;
    public boolean locked;
    public double initialScreenshakeX;
    public double initialScreenshakeY;
    public double screenshakeX;
    public double screenshakeY;
    public int screenshakeDuration = 60;
    public int screenshakeTimer = 0;
    public void setTarget(double x, double y) {
        if (locked) return;
        targetX = x;
        targetY = y;
    }
    public void update() {
        x += (targetX - x) * 0.1;
        y += (targetY - y) * 0.1;
        screenshakeTimer++;
        if (screenshakeTimer >= screenshakeDuration) {
            screenshakeX = 0;
            screenshakeY = 0;
        }
        else {
            screenshakeX = Math.pow(1 - (screenshakeTimer / (double)screenshakeDuration), 3) * initialScreenshakeX;
            screenshakeY = Math.pow(1 - (screenshakeTimer / (double)screenshakeDuration), 3) * initialScreenshakeY;
        }
    }
    public void snap() {
        x = targetX;
        y = targetY;
    }
    public void screenshake(double xAmount, double yAmount, int duration) {
        this.initialScreenshakeX += xAmount;
        this.initialScreenshakeY += yAmount;
        this.screenshakeDuration += duration;
        screenshakeTimer = 0;
    }
    public void screenshake(double amount, int duration) {
        screenshake(amount, amount, duration);
    }
    public void screenshake(double amount) {
        screenshake(amount, amount, 60);
    }
}
