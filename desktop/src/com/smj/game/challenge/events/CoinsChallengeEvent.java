package com.smj.game.challenge.events;

import com.smj.game.Game;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;

public class CoinsChallengeEvent extends TimerChallengeEvent {
    public int target;
    public CoinsChallengeEvent(int target) {
        this.target = target;
    }
    public int update(Medals medals) {
        if (Game.savefile.coins >= target) Game.finishLevel();
        return super.update(medals);
    }
    public boolean show(HUDElement element) {
        return element == HUDLayout.COIN_COUNTER;
    }
}
