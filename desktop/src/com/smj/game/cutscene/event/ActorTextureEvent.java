package com.smj.game.cutscene.event;

import com.badlogic.gdx.graphics.Texture;
import com.smj.game.cutscene.Actor;
import com.smj.game.cutscene.Cutscene;

public class ActorTextureEvent extends CutsceneEvent {
    public Actor actor;
    public Texture texture;
    public ActorTextureEvent(int frame, Actor actor, Texture texture) {
        super(frame);
        this.actor = actor;
        this.texture = texture;
    }
    public void run(Cutscene cutscene) {
        actor.texture = texture;
    }
}
