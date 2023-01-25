package com.smj.game.achievement;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

public class MultiTaskAchievement extends Achievement {
    public final int parts;
    public final boolean[] completed;
    public int progress;
    public MultiTaskAchievement(Texture icon, String name, String description, boolean hidden, int parts) {
        super(icon, name, description, hidden);
        this.parts = parts;
        completed = new boolean[parts];
    }
    public void achieve(Object... args) {
        if (!completed[(int)args[0]]) progress++;
        completed[(int)args[0]] = true;
    }
    public int getProgress() {
        return progress;
    }
    public int getMaxProgress() {
        return parts;
    }
    public void read(ObjectElement element) {
        ListElement list = element.getList("completed");
        for (int i = 0; i < parts; i++) {
            completed[i] = list.getBoolean(i);
        }
        progress = element.getInt("totalProgress");
    }
    public void write(ObjectElement element) {
        ListElement list = new ListElement();
        for (int i = 0; i < parts; i++) {
            list.addBoolean(completed[i]);
        }
        element.setList("completed", list);
        element.setInt("totalProgress", progress);
    }
}
