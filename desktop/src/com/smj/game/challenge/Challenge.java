package com.smj.game.challenge;

import com.smj.game.Game;
import com.smj.game.SaveFile;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.Renderer;

import java.util.HashMap;

public class Challenge {
    public ChallengeEvent event = null;
    public Medals medals = new Medals(0, 0, 0);
    public ChallengeType type = ChallengeType.MISC;
    public int difficulty = 0;
    public int level = 0;
    public int timer = 0;
    public int powerup = 0;
    public Integer highScore = null;
    public int score = 0;
    public void update() {
        if (event == null) return;
        score = event.update(medals);
    }
    public void init() {
        if (event == null) return;
        Game.savefile = new SaveFile();
        event.init(medals);
    }
    public void render(Renderer renderer) {
        if (event == null) return;
        event.render(renderer, medals);
    }
    public boolean show(HUDElement element) {
        if (element == HUDLayout.TIMER) return true;
        if (event == null) return false;
        return event.show(element);
    }
    public Challenge event(ChallengeEvent event) {
        this.event = event;
        return this;
    }
    public Challenge medals(Medals medals) {
        this.medals = medals;
        return this;
    }
    public Challenge type(ChallengeType type) {
        this.type = type;
        return this;
    }
    public Challenge difficulty(int difficulty) {
        this.difficulty = difficulty;
        return this;
    }
    public Challenge level(int level) {
        this.level = level;
        return this;
    }
    public Challenge powerup(int powerup) {
        this.powerup = powerup;
        return this;
    }
    public Challenge timer(int timer) {
        this.timer = timer;
        return this;
    }
}
