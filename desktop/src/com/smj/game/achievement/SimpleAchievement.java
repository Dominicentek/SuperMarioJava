package com.smj.game.achievement;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.bjson.ObjectElement;

public class SimpleAchievement extends Achievement {
    public final int maxProgress;
    public int progress = 0;
    public SimpleAchievement(Texture icon, String name, String description, boolean hidden, int maxProgress) {
        super(icon, name, description, hidden);
        this.maxProgress = maxProgress;
    }
    public void achieve(Object... args) {
        if (progress == maxProgress) return;
        progress++;
    }
    public int getProgress() {
        return progress;
    }
    public int getMaxProgress() {
        return maxProgress;
    }
    public void read(ObjectElement element) {
        progress = element.getInt("progress");
    }
    public void write(ObjectElement element) {
        element.setInt("progress", progress);
    }
}
