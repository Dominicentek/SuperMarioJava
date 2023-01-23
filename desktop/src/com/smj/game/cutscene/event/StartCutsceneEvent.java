package com.smj.game.cutscene.event;

import com.smj.Main;
import com.smj.game.cutscene.Cutscene;

public class StartCutsceneEvent extends CutsceneEvent {
    public String cutsceneID;
    public StartCutsceneEvent(int frame, String cutsceneID) {
        super(frame);
        this.cutsceneID = cutsceneID;
    }
    public void run(Cutscene cutscene) {
        Main.startCutscene(cutsceneID);
    }
}
