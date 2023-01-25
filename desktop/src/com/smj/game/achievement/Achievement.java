package com.smj.game.achievement;

import com.badlogic.gdx.graphics.Texture;
import com.smj.util.bjson.ObjectElement;

import java.util.HashMap;

public abstract class Achievement {
    public static final HashMap<String, Achievement> achievements = new HashMap<>();
    public final Texture icon;
    public final String name;
    public final String description;
    public final boolean hidden;
    public Achievement(Texture icon, String name, String description, boolean hidden) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.hidden = hidden;
    }
    public abstract void achieve(Object... args);
    public abstract int getProgress();
    public abstract int getMaxProgress();
    public abstract void read(ObjectElement element);
    public abstract void write(ObjectElement element);
    public static void achieve(String achievementID) {

    }
    public static void registerAchievements() {
        achievements.put("savior", new SimpleAchievement(null, "Our Savior", "Save Peach", false, 1));
        achievements.put("stomper", new SimpleAchievement(null, "Good Stomper", "Stomp on an enemy", false, 1));
        achievements.put("all_stomper", new MultiTaskAchievement(null, "Awesome Stomper", "Stomp on every stompable enemy", false, 5));
        achievements.put("wario", new SimpleAchievement(null, "Do the Wario", "Beat the game while collecting every coin", false, 1));
        achievements.put("barbeque", new SimpleAchievement(null, "Barbeque", "Beat the game while never collecting a coin", false, 1));
        achievements.put("powerups", new MultiTaskAchievement(null, "Good Stomper", "Collect every powerup", false, 6));
        achievements.put("immortal", new SimpleAchievement(null, "Immortal", "Max out your life counter", false, 1));
        achievements.put("head_surgery", new SimpleAchievement(null, "Head Surgery", "Beat the game while hitting every ? block", false, 1));
        achievements.put("fireworks", new SimpleAchievement(null, "Fireworks", "Beat a level with fireworks", false, 1));
        achievements.put("secret_coin", new SimpleAchievement(null, "Secret Coin", "Find a secret coin", false, 1));
        achievements.put("starring", new SimpleAchievement(null, "Starring Performance", "Finish a level with superstar active", false, 1));
        achievements.put("GLaDOS", new SimpleAchievement(null, "The cake is a lie", "Portal reference???", true, 1));
        achievements.put("no_life", new SimpleAchievement(null, "No life", "Beat the game 25 times (why)", true, 25));
        achievements.put("koopa_stomper", new SimpleAchievement(null, "Koopa Stomper", "Beat the game while stomping every koopa", true, 1));
    }
}
