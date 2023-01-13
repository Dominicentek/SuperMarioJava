package com.smj.game.cutscene.keyframe;

import com.smj.game.cutscene.Actor;
import com.smj.game.cutscene.Cutscene;
import com.smj.util.FieldInstance;
import com.smj.util.HashMapBuilder;

import java.util.HashMap;

public class ActorKeyframe extends Keyframe {
    public Actor actor;
    public int x;
    public int y;
    public ActorKeyframe(int frame, KeyframeType type, Actor actor, int x, int y) {
        super(frame, type);
        this.actor = actor;
        this.x = x;
        this.y = y;
    }
    public HashMap<FieldInstance, Integer> run(Cutscene cutscene) {
        return new HashMapBuilder<FieldInstance, Integer>()
            .add(new FieldInstance(actor, "x"), x)
            .add(new FieldInstance(actor, "y"), y)
            .get();
    }
    public boolean isNextKeyframeFor(Keyframe keyframe) {
        return keyframe instanceof ActorKeyframe && actor == ((ActorKeyframe)keyframe).actor;
    }
}
