package com.smj.game.cutscene;

import com.smj.game.cutscene.keyframe.KeyframeType;
import com.smj.util.FieldInstance;

public class Movement {
    public FieldInstance instance;
    public int from;
    public int to;
    public int value;
    public int length;
    public int frame = 0;
    public KeyframeType type;
    public Movement(FieldInstance instance, int from, int to, int length, KeyframeType type) {
        System.out.println(from);
        this.instance = instance;
        this.from = from;
        this.to = to;
        this.length = length;
        this.type = type;
        System.out.println(from);
        value = from;
    }
    public void update() {
        if (type == KeyframeType.LINEAR) value = (int)(frame / (double)length * (to - from));
        else if (type == KeyframeType.WAIT) {
            if (frame - 1 == length) value = to - from;
            else value = 0;
        }
        else if (type == KeyframeType.SMOOTH) {
            if (frame / 2 < length) {
                value = (int)((Math.sin(Math.toRadians(frame / (double)length * 180 - 90)) + 1) / 2.0 * (to - from));
            }
        }
        instance.set(value + from);
        frame++;
    }
    public boolean finished() {
        return frame == length;
    }
}
