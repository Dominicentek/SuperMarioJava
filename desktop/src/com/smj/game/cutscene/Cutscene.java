package com.smj.game.cutscene;

import com.badlogic.gdx.Gdx;
import com.smj.Main;
import com.smj.game.cutscene.event.*;
import com.smj.game.cutscene.keyframe.ActorKeyframe;
import com.smj.game.cutscene.keyframe.CameraKeyframe;
import com.smj.game.cutscene.keyframe.Keyframe;
import com.smj.game.cutscene.keyframe.KeyframeType;
import com.smj.util.AudioPlayer;
import com.smj.util.FieldInstance;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

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
        renderer.translate(cameraX, cameraY);
        for (Actor actor : actors) {
            renderer.draw(actor.texture, actor.x, actor.y);
        }
        renderer.translate(-cameraX, -cameraY);
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
        renderer.drawString(currentDialog.text.substring(0, currentDialog.progress / DIAGSPD_INVERTED), x + 32, y + 8);
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
    }
}
