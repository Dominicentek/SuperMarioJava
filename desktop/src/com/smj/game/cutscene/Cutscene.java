package com.smj.game.cutscene;

import com.smj.Main;
import com.smj.game.cutscene.event.*;
import com.smj.game.cutscene.event.MoveType;
import com.smj.game.options.Controls;
import com.smj.gui.font.Font;
import com.smj.util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Cutscene {
    public static final HashMap<String, CutsceneBuilder> cutscenes = new HashMap<>();
    public static final float DIALOG_SPEED = 0.25f;
    private static final int DIAGSPD_INVERTED = (int)(1 / DIALOG_SPEED);
    public ArrayList<CutsceneEvent> events = new ArrayList<>();
    public ArrayList<Actor> actors = new ArrayList<>();
    public ArrayList<Movement> movements = new ArrayList<>();
    public int currentFrame = -1;
    public int cameraX = 0;
    public int cameraY = 0;
    public Dialog currentDialog = null;
    public boolean skippable = true;
    public void update() {
        for (CutsceneEvent event : events) {
            if (event.frame == currentFrame) event.run(this);
        }
        for (Movement movement : new ArrayList<>(movements)) {
            movement.update();
            if (movement.finished()) {
                movements.remove(movement);
                movement.instance.set(movement.to);
            }
        }
        if (currentDialog != null) {
            if (currentDialog.progress < currentDialog.text.length() * DIAGSPD_INVERTED) {
                currentDialog.progress++;
                if (currentDialog.progress % DIAGSPD_INVERTED == 0 && !Character.isWhitespace(currentDialog.text.charAt(Math.min(currentDialog.progress / DIAGSPD_INVERTED, currentDialog.text.length() - 1)))) AudioPlayer.BEEP.play();
            }
        }
        if (Controls.JUMP.isJustPressed() && skippable) {
            new EndEvent(0, 0.5).run(this);
        }
        currentFrame++;
    }
    public void render(Renderer renderer) {
        renderer.setColor(0xFFFFFFFF);
        renderer.translate(-cameraX, -cameraY);
        for (Actor actor : actors) {
            renderer.draw(actor.texture, actor.region, actor.x, actor.y);
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
        int lastSpace = 0;
        int wordWrap = linelen;
        for (int i = 0; i < currentDialog.text.length(); i++) {
            if (currentDialog.text.charAt(i) == ' ') lastSpace = i;
            if (i == linelen) {
                wordWrap = lastSpace == 0 ? linelen : lastSpace;
                break;
            }
        }
        renderer.drawString(currentDialog.text.substring(0, Math.min(chars, wordWrap)).trim(), x + 32, y + 8);
        wordWrap++;
        if (chars >= wordWrap) renderer.drawString(currentDialog.text.substring(wordWrap, chars).trim(), x + 32, y + 16);
    }
    public static void loadCutscenes() {
        Actor mario = new Actor(0, 0, TextureLoader.get("images/icons/mario.png"));
        cutscenes.put("test", new CutsceneBuilder()
            .addActor(new Actor(0, 0, TextureLoader.get("images/themes/0/background.png")))
            .addActor(mario)
            .addEvent(new MoveActorEvent(CutsceneEvent.beginning(120), mario, 100, 150, 120, MoveType.LINEAR))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(), mario, 150, 50, 120, MoveType.WAIT))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(), mario, 50, 100, 120, MoveType.SMOOTH))
            .addEvent(new MoveCameraEvent(CutsceneEvent.beginning(120), Main.WIDTH / 2, Main.HEIGHT / 2, 120, MoveType.LINEAR))
            .addEvent(new MoveCameraEvent(CutsceneEvent.after(), -Main.WIDTH / 2, -Main.HEIGHT / 2, 120, MoveType.WAIT))
            .addEvent(new MoveCameraEvent(CutsceneEvent.after(), 0, 0, 120, MoveType.SMOOTH))
            .addEvent(new MusicEvent(CutsceneEvent.beginning(), null))
            .addEvent(new DialogEvent(CutsceneEvent.simultaneously(), "test", 0))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "test", 1))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "test", 2))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "test", 3))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "test", 4))
            .addEvent(new RemoveDialogEvent(CutsceneEvent.after(30)))
            .addEvent(new EndEvent(CutsceneEvent.simultaneously(), 0.5))
        );
        Actor bowser = new Actor(680, -32, TextureLoader.get("images/cutscene/bowser.png"));
        Actor peach = new Actor(726, 140, TextureLoader.get("images/cutscene/peach.png"));
        cutscenes.put("beginning", new CutsceneBuilder()
            .addActor(new Actor(0, 0, TextureLoader.get("images/cutscene/beginning_bg.png")))
            .addActor(new Actor(392, 176, TextureLoader.get("images/cutscene/mario.png")))
            .addActor(peach)
            .addActor(bowser)
            .addEvent(new MusicEvent(CutsceneEvent.beginning(), AudioPlayer.MUSIC[6]))
            .addEvent(new MoveCameraEvent(CutsceneEvent.simultaneously(), 384, 0, 180, MoveType.WAIT))
            .addEvent(new DialogEvent(CutsceneEvent.simultaneously(), "beginning", 0))
            .addEvent(new RemoveDialogEvent(CutsceneEvent.simultaneously(180)))
            .addEvent(new DialogEvent(CutsceneEvent.after(60), "beginning", 1))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "beginning", 2))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(60), bowser, 680, 144, 10, MoveType.LINEAR))
            .addEvent(new MusicEvent(CutsceneEvent.after(), AudioPlayer.MUSIC[8]))
            .addEvent(new SFXEvent(CutsceneEvent.simultaneously(), AudioPlayer.EXPLOSION))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "beginning", 3))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "beginning", 4))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(30), bowser, 768, 144, 60, MoveType.LINEAR))
            .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(30), peach, 792, 140, 30, MoveType.LINEAR))
            .addEvent(new DialogEvent(CutsceneEvent.simultaneously(), "beginning", 5))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "beginning", 6))
            .addEvent(new EndEvent(CutsceneEvent.after(30), 0.5))
        );
        for (int i = 1; i <= 7; i++) {
            Actor castleMario = new Actor(-16, 176, TextureLoader.get("images/cutscene/mario.png"));
            cutscenes.put("in_another_castle_" + i, new CutsceneBuilder()
                .addActor(new Actor(0, 0, TextureLoader.get("images/cutscene/castleend.png")))
                .addActor(new Actor(290, 115, TextureLoader.get("images/cutscene/toad.png")))
                .addActor(new Actor(285, 16, TextureLoader.get("images/cutscene/cage.png")))
                .addActor(castleMario)
                .addEvent(new MusicEvent(CutsceneEvent.beginning(), AudioPlayer.MUSIC[8]))
                .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(), castleMario, 200, 176, 120, MoveType.SMOOTH))
                .addEvent(new DialogEvent(CutsceneEvent.after(), "in_another_castle_" + i, 0))
                .addEvent(new DialogEvent(CutsceneEvent.after(30), "in_another_castle_" + i, 1))
                .addEvent(new DialogEvent(CutsceneEvent.after(30), "in_another_castle_" + i, 2))
                .addEvent(new EndEvent(CutsceneEvent.after(60), 0.5))
            );
        }
        Actor castleMario = new Actor(-16, 176, TextureLoader.get("images/cutscene/mario.png"));
        Actor ground = new Actor(272, 256, TextureLoader.get("images/cutscene/ground.png"));
        Actor cage = new Actor(285, 16, TextureLoader.get("images/cutscene/cage.png"));
        Actor castlePeach = new Actor(290, 115, TextureLoader.get("images/cutscene/peach.png"));
        String theEndText = GameStrings.get("ending1");
        String thanksForPlayingText = GameStrings.get("ending2");
        Actor text1 = new Actor(192 - Font.stringWidth(theEndText) / 2, -20, Font.getTexture(theEndText));
        Actor text2 = new Actor(192 - Font.stringWidth(thanksForPlayingText) / 2, -8, Font.getTexture(thanksForPlayingText));
        cutscenes.put("peach_saved", new CutsceneBuilder()
            .addActor(new Actor(0, 0, TextureLoader.get("images/cutscene/castleend.png")))
            .addActor(castlePeach)
            .addActor(cage)
            .addActor(ground)
            .addActor(castleMario)
            .addActor(text1)
            .addActor(text2)
            .addActor(new Actor(272, -48, TextureLoader.get("images/cutscene/ground.png")))
            .addEvent(new MusicEvent(CutsceneEvent.beginning(), AudioPlayer.MUSIC[14]))
            .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(), castleMario, 200, 176, 120, MoveType.SMOOTH))
            .addEvent(new DialogEvent(CutsceneEvent.after(), "peach_saved", 0))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 1))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 2))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 3))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 4))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 5))
            .addEvent(new DialogEvent(CutsceneEvent.after(30), "peach_saved", 6))
            .addEvent(new RemoveDialogEvent(CutsceneEvent.after(30)))
            .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(), ground, 272, 192, 120, MoveType.SMOOTH))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(), cage, 285, -110, 120, MoveType.SMOOTH))
            .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(), castlePeach, 290, 168, 120, MoveType.LINEAR))
            .addEvent(new MoveActorEvent(CutsceneEvent.after(60), text1, text1.x, 118, 120, MoveType.SMOOTH))
            .addEvent(new MoveActorEvent(CutsceneEvent.simultaneously(), text2, text2.x, 130, 120, MoveType.SMOOTH))
            .unskippable()
        );
    }
}
