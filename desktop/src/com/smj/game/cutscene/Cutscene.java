package com.smj.game.cutscene;

import com.badlogic.gdx.Gdx;
import com.smj.Main;
import com.smj.game.cutscene.event.*;
import com.smj.game.cutscene.keyframe.ActorKeyframe;
import com.smj.game.cutscene.keyframe.CameraKeyframe;
import com.smj.game.cutscene.keyframe.Keyframe;
import com.smj.game.cutscene.keyframe.KeyframeType;
import com.smj.util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Cutscene {
    public static final HashMap<String, CutsceneBuilder> cutscenes = new HashMap<>();
    public static final float DIALOG_SPEED = 0.25f;
    private static final int DIAGSPD_INVERTED = (int)(1 / DIALOG_SPEED);
    public ArrayList<Keyframe> keyframes = new ArrayList<>();
    public ArrayList<CutsceneEvent> events = new ArrayList<>();
    public ArrayList<Actor> actors = new ArrayList<>();
    public ArrayList<Movement> movements = new ArrayList<>();
    public int currentFrame = -1;
    public int cameraX = 0;
    public int cameraY = 0;
    public Dialog currentDialog = null;
    public void update() {
        for (CutsceneEvent event : events) {
            if (event.frame == currentFrame) event.run(this);
        }
        for (int i = 0; i < keyframes.size(); i++) {
            Keyframe keyframe = keyframes.get(i);
            if (keyframe.frame == currentFrame) {
                HashMap<FieldInstance, Integer> fields = keyframe.run(this);
                Keyframe nextKeyframe = null;
                for (int j = i + 1; j < keyframes.size(); j++) {
                    if (keyframes.get(j).isNextKeyframeFor(keyframe)) {
                        nextKeyframe = keyframes.get(j);
                        break;
                    }
                }
                for (FieldInstance field : fields.keySet()) {
                    field.set(fields.get(field));
                }
                if (nextKeyframe != null) {
                    HashMap<FieldInstance, Integer> nextFields = nextKeyframe.run(this);
                    for (FieldInstance field : fields.keySet()) {
                        Integer value = null;
                        for (FieldInstance f : nextFields.keySet()) {
                            if (field.equals(f)) {
                                value = nextFields.get(f);
                                break;
                            }
                        }
                        if (value == null) continue;
                        movements.add(new Movement(field, fields.get(field), value, nextKeyframe.frame - keyframe.frame, keyframe.type));
                    }
                }
            }
        }
        for (Movement movement : new ArrayList<>(movements)) {
            movement.update();
            if (movement.finished()) movements.remove(movement);
        }
        if (currentDialog != null) {
            if (currentDialog.progress < currentDialog.text.length() * DIAGSPD_INVERTED) {
                currentDialog.progress++;
                if (currentDialog.progress % DIAGSPD_INVERTED == 0 && !Character.isWhitespace(currentDialog.text.charAt(Math.min(currentDialog.progress / DIAGSPD_INVERTED, currentDialog.text.length() - 1)))) AudioPlayer.BEEP.play();
            }
        }
        currentFrame++;
    }
    public void render(Renderer renderer) {
        renderer.setColor(0xFFFFFFFF);
        renderer.translate(-cameraX, -cameraY);
        for (Actor actor : actors) {
            renderer.draw(actor.texture, actor.x, actor.y);
        }
        renderer.translate(cameraX, cameraY);
        if (currentDialog == null) return;
        int x = 0;
        int y = Main.HEIGHT - 32;
        int w = Main.WIDTH;
        int h = 32;
        int blue = 0xAF;
        for (int i = 0; i < h; i++) {
            renderer.setColor((blue << 8) | 0xFF);
            renderer.rect(x, y + i, w, 1);
            blue -= 4;
        }
        renderer.setColor(0xFFFFFFFF);
        renderer.rect(x, y, 1, h);
        renderer.rect(x, y, w, 1);
        renderer.rect(x + w - 1, y, 1, h);
        renderer.rect(x, y + h - 1, w, 1);
        if (currentDialog.icon != null) renderer.draw(currentDialog.icon, x + 8, y + 8);
        int linelen = (Main.WIDTH - 40) / 8;
        int chars = currentDialog.progress / DIAGSPD_INVERTED;
        renderer.drawString(currentDialog.text.substring(0, Math.min(chars, linelen)).trim(), x + 32, y + 8);
        if (chars >= linelen) renderer.drawString(currentDialog.text.substring(linelen, Math.min(chars, linelen * 2)).trim(), x + 32, y + 16);
    }
    static {
        Actor mario = new Actor(0, 0, TextureLoader.get(Gdx.files.internal("assets/images/icons/mario.png")));
        cutscenes.put("test", new CutsceneBuilder()
            .addActor(new Actor(0, 0, TextureLoader.get(Gdx.files.internal("assets/images/themes/0/background.png"))))
            .addActor(mario)
            .addKeyframe(new ActorKeyframe(120, KeyframeType.SMOOTH, mario, 0, 0))
            .addKeyframe(new ActorKeyframe(240, KeyframeType.LINEAR, mario, 100, 150))
            .addKeyframe(new ActorKeyframe(360, KeyframeType.WAIT, mario, 150, 50))
            .addKeyframe(new ActorKeyframe(480, KeyframeType.SMOOTH, mario, 50, 100))
            .addKeyframe(new CameraKeyframe(120, KeyframeType.SMOOTH, 0, 0))
            .addKeyframe(new CameraKeyframe(240, KeyframeType.LINEAR, Main.WIDTH / 2, Main.HEIGHT / 2))
            .addKeyframe(new CameraKeyframe(360, KeyframeType.WAIT, -Main.WIDTH / 2, -Main.HEIGHT / 2))
            .addKeyframe(new CameraKeyframe(480, KeyframeType.SMOOTH, 0, 0))
            .addEvent(new MusicEvent(0, null))
            .addEvent(new DialogEvent(0, "test", 0))
            .addEvent(new DialogEvent(120, "test", 1))
            .addEvent(new DialogEvent(240, "test", 2))
            .addEvent(new DialogEvent(360, "test", 3))
            .addEvent(new DialogEvent(480, "test", 4))
            .addEvent(new RemoveDialogEvent(600))
            .addEvent(new EndEvent(600, 0.5))
        );
        Actor bowser = new Actor(680, -32, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/bowser.png")));
        Actor peach = new Actor(726, 140, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/peach.png")));
        cutscenes.put("beginning", new CutsceneBuilder()
            .addActor(new Actor(0, 0, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/beginning_bg.png"))))
            .addActor(new Actor(392, 176, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/mario.png"))))
            .addActor(peach)
            .addActor(bowser)
            .addKeyframe(new CameraKeyframe(0, KeyframeType.WAIT, 0, 0))
            .addKeyframe(new CameraKeyframe(180, KeyframeType.WAIT, 384, 0))
            .addKeyframe(new ActorKeyframe(600, KeyframeType.LINEAR, bowser, 680, -32))
            .addKeyframe(new ActorKeyframe(610, KeyframeType.WAIT, bowser, 680, 144))
            .addKeyframe(new ActorKeyframe(1000, KeyframeType.LINEAR, bowser, 680, 144))
            .addKeyframe(new ActorKeyframe(1060, KeyframeType.LINEAR, bowser, 768, 144))
            .addKeyframe(new ActorKeyframe(0, KeyframeType.WAIT, peach, 726, 140))
            .addKeyframe(new ActorKeyframe(1030, KeyframeType.LINEAR, peach, 726, 140))
            .addKeyframe(new ActorKeyframe(1060, KeyframeType.LINEAR, peach, 792, 140))
            .addEvent(new MusicEvent(0, AudioPlayer.MUSIC[6]))
            .addEvent(new DialogEvent(60, "beginning", 0))
            .addEvent(new RemoveDialogEvent(180))
            .addEvent(new DialogEvent(240, "beginning", 1))
            .addEvent(new DialogEvent(360, "beginning", 2))
            .addEvent(new MusicEvent(610, AudioPlayer.MUSIC[8]))
            .addEvent(new SFXEvent(610, AudioPlayer.EXPLOSION))
            .addEvent(new DialogEvent(660, "beginning", 3))
            .addEvent(new DialogEvent(900, "beginning", 4))
            .addEvent(new DialogEvent(1020, "beginning", 5))
            .addEvent(new DialogEvent(1140, "beginning", 6))
            .addEvent(new EndEvent(1320, 0.5))
        );
        for (int i = 1; i <= 7; i++) {
            Actor castleMario = new Actor(-16, 176, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/mario.png")));
            cutscenes.put("in_another_castle_" + i, new CutsceneBuilder()
                .addActor(new Actor(0, 0, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/castleend.png"))))
                .addActor(new Actor(285, 16, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/cage.png"))))
                .addActor(new Actor(290, 115, TextureLoader.get(Gdx.files.internal("assets/images/cutscene/toad.png"))))
                .addActor(castleMario)
                .addKeyframe(new ActorKeyframe(0, KeyframeType.SMOOTH, castleMario, -16, 176))
                .addKeyframe(new ActorKeyframe(120, KeyframeType.LINEAR, castleMario, 200, 176))
                .addEvent(new MusicEvent(0, AudioPlayer.MUSIC[8]))
                .addEvent(new DialogEvent(120, "in_another_castle_" + i, 0))
                .addEvent(new DialogEvent(240, "in_another_castle_" + i, 1))
                .addEvent(new DialogEvent(420, "in_another_castle_" + i, 2))
                .addEvent(new EndEvent(540, 0.5))
            );
        }
    }
}
