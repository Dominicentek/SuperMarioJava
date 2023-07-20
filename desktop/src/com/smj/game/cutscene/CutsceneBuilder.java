package com.smj.game.cutscene;

import com.smj.game.cutscene.event.CutsceneEvent;

import java.util.ArrayList;

public class CutsceneBuilder {
    public ArrayList<CutsceneEvent> events = new ArrayList<>();
    public ArrayList<Actor> actors = new ArrayList<>();
    public boolean skippable = true;
    private static CutsceneBuilder current;
    public CutsceneBuilder() {
        current = this;
    }
    public CutsceneBuilder addActor(Actor actor) {
        actors.add(actor);
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
    public static CutsceneBuilder current() {
        return current;
    }
    public Cutscene build() {
        Cutscene cutscene = new Cutscene();
        cutscene.skippable = skippable;
        cutscene.events.addAll(events);
        for (Actor actor : actors) {
            cutscene.actors.add(actor.restore());
        }
        return cutscene;
    }
    public CutsceneEvent getLastEvent() {
        return events.get(events.size() - 1);
    }
}
