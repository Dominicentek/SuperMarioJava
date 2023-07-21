package com.smj.game.cutscene;

import com.smj.game.cutscene.event.MoveType;
import com.smj.util.FieldInstance;

public class Movement {
    public FieldInstance instance;
    public int from;
    public int to;
    public int value;
    public int length;
    public int frame = 0;
    public MoveType type;
    public Movement(FieldInstance instance, int from, int to, int length, MoveType type) {
        this.instance = instance;
        this.from = from;
        this.to = to;
        this.length = length;
        this.type = type;
        value = from;
    }
    public void update() {
        if (type == MoveType.LINEAR) value = (int)(frame / (double)length * (to - from));
        else if (type == MoveType.WAIT) {
            if (frame + 1 == length) value = to - from;
            else value = 0;
        }
        else if (type == MoveType.SMOOTH) {
            if (frame / 2 < length) {
                double x = frame / (double)length;
                value = (int)((x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2) * (to - from));
            }
        }
        instance.set(value + from);
        frame++;
    }
    public boolean finished() {
        return frame == length;
    }
}
