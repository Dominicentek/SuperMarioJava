package com.smj.util;

import com.smj.game.Game;

public class Transition {
    private long time;
    private double fadeSeconds;
    private double currentTime;
    private Runnable runnable;
    private boolean started;
    public int stage = 0;
    public int alpha = 0;
    public Transition(double fadeSeconds, Runnable runnable) {
        this.fadeSeconds = fadeSeconds;
        this.runnable = runnable;
    }
    public void start() {
        time = System.nanoTime();
        started = true;
    }
    public void update() {
        if (!started) return;
        if (stage == 2) return;
        Game.paused = true;
        long time = System.nanoTime();
        double seconds = (time - this.time) / 1000000000.0;
        this.time = time;
        currentTime += seconds;
        if (currentTime >= fadeSeconds) {
            currentTime = 0;
            if (stage == 0) runnable.run();
            if (stage < 2) stage++;
            if (stage == 2) Game.paused = false;
        }
        alpha = (int)Math.min(255, (((stage == 1 ? fadeSeconds : 0) - currentTime) / fadeSeconds * 255));
        if (stage == 0) alpha *= -1;
    }
}
