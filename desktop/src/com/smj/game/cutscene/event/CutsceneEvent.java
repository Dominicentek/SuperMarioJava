package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;
import com.smj.game.cutscene.CutsceneBuilder;

public abstract class CutsceneEvent {
    public int frame;
    public CutsceneEvent(int frame) {
        this.frame = frame;
    }
    public abstract void run(Cutscene cutscene);
    public int duration() {
        return 0;
    }
    public static int after(int delay) {
        CutsceneEvent event = CutsceneBuilder.current().getLastEvent();
        return event.frame + event.duration() + delay;
    }
    public static int simultaneously(int delay) {
        CutsceneEvent event = CutsceneBuilder.current().getLastEvent();
        return event.frame + delay;
    }
    public static int beginning(int delay) {
        return delay;
    }
    public static int after() {
        return after(0);
    }
    public static int simultaneously() {
        return simultaneously(0);
    }
    public static int beginning() {
        return beginning(0);
    }
}
