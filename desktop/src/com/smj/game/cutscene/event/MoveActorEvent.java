package com.smj.game.cutscene.event;

import com.smj.game.cutscene.Actor;
import com.smj.game.cutscene.Cutscene;
import com.smj.game.cutscene.CutsceneBuilder;
import com.smj.game.cutscene.Movement;
import com.smj.util.FieldInstance;

public class MoveActorEvent extends CutsceneEvent {
    public Actor actor;
    public int x;
    public int y;
    public int duration;
    public MoveType type;
    public MoveActorEvent(int frame, Actor actor, int x, int y, int duration, MoveType type) {
        super(frame);
        this.actor = actor;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.type = type;
    }
    public void run(Cutscene cutscene) {
        cutscene.movements.add(new Movement(new FieldInstance(actor, "x"), actor.x, x, duration, type));
        cutscene.movements.add(new Movement(new FieldInstance(actor, "y"), actor.y, y, duration, type));
    }
    public int duration() {
        return duration;
    }
}
