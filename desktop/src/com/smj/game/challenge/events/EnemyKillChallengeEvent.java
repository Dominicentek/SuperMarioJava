package com.smj.game.challenge.events;

import com.smj.game.Game;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;

public class EnemyKillChallengeEvent extends TimerChallengeEvent {
    public int target;
    public EnemyKillChallengeEvent(int target) {
        this.target = target;
    }
    public void init(Medals medals) {
        super.init(medals);
    }
    public int update(Medals medals) {
        if (Game.enemyKills >= target) Game.finishLevel();
        return super.update(medals);
    }
}
