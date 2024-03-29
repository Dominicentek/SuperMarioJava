package com.smj.game;

import com.smj.game.options.Controls;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.Saveable;
import com.smj.util.bjson.ObjectElement;

public class SaveFile implements Saveable {
    public int coins = 0;
    public int lives = 3;
    public int score = 0;
    public int levelsCompleted = 0;
    public int powerupState = 0;
    public SaveFile() {
        correctHUD();
    }
    public SaveFile(ObjectElement element) {
        coins = Byte.toUnsignedInt(element.getByte("coins"));
        lives = Byte.toUnsignedInt(element.getByte("lives"));
        score = element.getInt("score");
        levelsCompleted = Byte.toUnsignedInt(element.getByte("progress"));
        powerupState = Byte.toUnsignedInt(element.getByte("powerup"));
        correctHUD();
    }
    private void correctHUD() {
        HUDLayout.COIN_COUNTER.attachment.set(coins);
        HUDLayout.LIFE_COUNTER.attachment.set(lives);
        HUDLayout.SCORE.set(score);
    }
    public ObjectElement save() {
        ObjectElement element = new ObjectElement();
        element.setByte("coins", (byte)coins);
        element.setByte("lives", (byte)lives);
        element.setInt("score", score);
        element.setByte("progress", (byte)levelsCompleted);
        element.setByte("powerup", (byte)powerupState);
        return element;
    }
    public String toString() {
        return "coin=" + coins + " life=" + lives + " point=" + score + " progress=" + levelsCompleted + " playerstate=" + powerupState;
    }
}
