package com.smj.game.challenge.events;

import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.Renderer;

public class TimelessCoinsChallengeEvent implements ChallengeEvent {
    public int coins = 0;
    public int target;
    public TimelessCoinsChallengeEvent(int coins) {
        target = coins;
    }
    public void init(Medals medals) {
        coins = 0;
    }
    public int update(Medals medals) {
        while (coins % 100 != Game.savefile.coins % 100) {
            coins++;
        }
        if (coins >= target) Game.finishLevel();
        return 0;
    }
    public void render(Renderer renderer, Medals medals) {}
    public boolean show(HUDElement element) {
        return element == HUDLayout.COIN_COUNTER;
    }
    public String getString(Medals medals, Integer value, boolean highScore) {
        if (value == null) return "Not completed";
        if (highScore) return "Completed";
        return null;
    }
}
