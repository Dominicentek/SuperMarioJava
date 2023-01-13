package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;
import com.smj.util.AudioPlayer;

public class SFXEvent extends CutsceneEvent {
    public AudioPlayer sound;
    public SFXEvent(int frame, AudioPlayer sound) {
        super(frame);
        this.sound = sound;
    }
    public void run(Cutscene cutscene) {
        sound.play();
    }
}
