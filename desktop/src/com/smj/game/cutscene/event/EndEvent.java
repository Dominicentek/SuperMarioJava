package com.smj.game.cutscene.event;

import com.smj.Main;
import com.smj.game.cutscene.Cutscene;
import com.smj.util.Transition;

public class EndEvent extends CutsceneEvent {
    public double fade;
    public EndEvent(int frame, double fade) {
        super(frame);
        this.fade = fade;
    }
    public void run(Cutscene cutscene) {
        Main.setTransition(new Transition(fade, () -> {
            Main.currentCutscene = null;
            if (Main.afterCutscene != null) {
                Main.afterCutscene.run();
                Main.afterCutscene = null;
            }
        }));
    }
}
