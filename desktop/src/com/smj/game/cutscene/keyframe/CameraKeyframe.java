package com.smj.game.cutscene.keyframe;

import com.smj.game.cutscene.Cutscene;
import com.smj.util.FieldInstance;
import com.smj.util.HashMapBuilder;

import java.util.HashMap;

public class CameraKeyframe extends Keyframe {
    public int x;
    public int y;
    public CameraKeyframe(int frame, KeyframeType type, int x, int y) {
        super(frame, type);
        this.x = x;
        this.y = y;
    }
    public HashMap<FieldInstance, Integer> run(Cutscene cutscene) {
        return new HashMapBuilder<FieldInstance, Integer>()
            .add(new FieldInstance(cutscene, "cameraX"), x)
            .add(new FieldInstance(cutscene, "cameraY"), y)
            .get();
    }
    public boolean isNextKeyframeFor(Keyframe keyframe) {
        return keyframe instanceof CameraKeyframe;
    }
}
