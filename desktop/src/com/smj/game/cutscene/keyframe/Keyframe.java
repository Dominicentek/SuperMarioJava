package com.smj.game.cutscene.keyframe;

import com.smj.game.cutscene.Cutscene;
import com.smj.util.FieldInstance;

import java.util.HashMap;

public abstract class Keyframe {
    public int frame;
    public KeyframeType type;
    public Keyframe(int frame, KeyframeType type) {
        this.frame = frame;
        this.type = type;
    }
    public abstract HashMap<FieldInstance, Integer> run(Cutscene cutscene);
    public abstract boolean isNextKeyframeFor(Keyframe keyframe);
}
