package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;

public class RemoveDialogEvent extends CutsceneEvent {
    public RemoveDialogEvent(int frame) {
        super(frame);
    }
    public void run(Cutscene cutscene) {
        cutscene.currentDialog = null;
    }
}
