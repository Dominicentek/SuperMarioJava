package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Cutscene;
import com.smj.game.cutscene.Movement;
import com.smj.util.FieldInstance;

public class MoveCameraEvent extends CutsceneEvent {
    public int x;
    public int y;
    public int duration;
    public MoveType type;
    public MoveCameraEvent(int frame, int x, int y, int duration, MoveType type) {
        super(frame);
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.type = type;
    }
    public void run(Cutscene cutscene) {
        cutscene.movements.add(new Movement(new FieldInstance(cutscene, "cameraX"), cutscene.cameraX, x, duration, type));
        cutscene.movements.add(new Movement(new FieldInstance(cutscene, "cameraY"), cutscene.cameraY, y, duration, type));
    }
    public int duration() {
        return duration;
    }
}
