package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;
import com.smj.game.cutscene.Dialog;

public class DialogEvent extends CutsceneEvent {
    public Dialog dialog;
    public DialogEvent(int frame, String scriptID, int dialogID) {
        super(frame);
        dialog = Dialog.dialogs.get(scriptID)[dialogID].copy();
    }
    public void run(Cutscene cutscene) {
        dialog.progress = 0;
        cutscene.currentDialog = dialog;
    }
    public int duration() {
        return dialog.text.length() * (int)(1 / Cutscene.DIALOG_SPEED);
    }
}
