package com.smj.game.challenge.events;

import com.smj.game.Game;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;

public class CoinsChallengeEvent extends TimerChallengeEvent {
    public int target;
    public int currTarget;
    public int prevValue = 0;
    public CoinsChallengeEvent(int target) {
        this.target = target;
    }
    public void init(Medals medals) {
        super.init(medals);
        prevValue = 0;
        currTarget = target;
    }
    public int update(Medals medals) {
        if (Game.savefile.coins >= currTarget) Game.finishLevel();
        if (Game.savefile.coins < prevValue) currTarget -= 100;
        prevValue = Game.savefile.coins;
        return super.update(medals);
    }
    public boolean show(HUDElement element) {
        return element == HUDLayout.COIN_COUNTER;
    }
}
