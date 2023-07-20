package com.smj.game.cutscene;

import com.smj.game.cutscene.event.CutsceneEvent;
import com.smj.game.cutscene.keyframe.Keyframe;

import java.util.ArrayList;

public class CutsceneBuilder {
    public ArrayList<Keyframe> keyframes = new ArrayList<>();
    public ArrayList<CutsceneEvent> events = new ArrayList<>();
    public ArrayList<Actor> actors = new ArrayList<>();
    public boolean skippable = true;
    public CutsceneBuilder addActor(Actor actor) {
        actors.add(actor);
        return this;
    }
    public CutsceneBuilder addKeyframe(Keyframe keyframe) {
        keyframes.add(keyframe);
        return this;
    }
    public CutsceneBuilder addEvent(CutsceneEvent event) {
        events.add(event);
        return this;
    }
    public CutsceneBuilder unskippable() {
        skippable = false;
        return this;
    }
    public Cutscene build() {
        Cutscene cutscene = new Cutscene();
        cutscene.skippable = skippable;
        cutscene.keyframes.addAll(keyframes);
        cutscene.events.addAll(events);
        for (Actor actor : actors) {
            cutscene.actors.add(actor.restore());
        }
        return cutscene;
    }
}
