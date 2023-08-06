package com.smj.game.challenge;

import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;

public interface ChallengeEvent {
    void init(Medals medals);
    int update(Medals medals);
    void render(Renderer renderer, Medals medals);
    boolean show(HUDElement element);
    String getString(Medals medals, Integer value);
}
