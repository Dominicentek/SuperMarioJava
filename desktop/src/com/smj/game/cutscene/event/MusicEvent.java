package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;
import com.smj.util.SMJMusic;

public class MusicEvent extends CutsceneEvent {
    public SMJMusic music;
    public MusicEvent(int frame, SMJMusic music) {
        super(frame);
        this.music = music;
    }
    public void run(Cutscene cutscene) {
        if (music == null) SMJMusic.stopMusic();
        else music.play();
    }
}
