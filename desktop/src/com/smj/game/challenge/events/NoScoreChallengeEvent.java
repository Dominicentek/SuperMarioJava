package com.smj.game.challenge.events;

import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;

public class NoScoreChallengeEvent implements ChallengeEvent {
    public void init(Medals medals) {}
    public int update(Medals medals) {
        if (Game.savefile.score > 0) Game.die();
        return 0;
    }
    public void render(Renderer renderer, Medals medals) {}
    public boolean show(HUDElement element) {
        return false;
    }
    public String getString(Medals medals, Integer value, boolean highScore) {
        if (value == null) return "Not completed";
        if (highScore) return "Completed";
        return null;
    }
}
