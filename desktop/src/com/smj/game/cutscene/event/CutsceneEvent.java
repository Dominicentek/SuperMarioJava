package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;

public abstract class CutsceneEvent {
    public int frame;
    public CutsceneEvent(int frame) {
        this.frame = frame;
    }
    public abstract void run(Cutscene cutscene);
}
