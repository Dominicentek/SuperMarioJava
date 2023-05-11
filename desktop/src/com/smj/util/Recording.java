package com.smj.util;

import com.smj.game.options.Controls;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.util.ArrayList;

public class Recording implements Saveable {
    public final int level;
    public ArrayList<RecordingFrame> frames = new ArrayList<>();
    public int length = 0;
    private RecordingFrame lastFrame;
    private int currentFrame = 0;
    private static final int DESYNC_FIX = 1;
    public Recording(ObjectElement element) {
        level = Short.toUnsignedInt(element.getShort("level"));
        ListElement frames = element.getList("frames");
        length = Short.toUnsignedInt(element.getShort("length"));
        for (int i = 0; i < frames.size(); i++) {
            ObjectElement frameData = frames.getObject(i);
            RecordingFrame frame = new RecordingFrame();
            frame.up = frameData.getBoolean("up");
            frame.left = frameData.getBoolean("left");
            frame.down = frameData.getBoolean("down");
            frame.right = frameData.getBoolean("right");
            frame.jump = frameData.getBoolean("jump");
            frame.run = frameData.getBoolean("run");
            frame.frame = frameData.getInt("frame");
            this.frames.add(frame);
        }
    }
    public Recording(int level) {
        this.level = level;
    }
    public void recordFrame() {
        RecordingFrame frame = new RecordingFrame();
        frame.frame = currentFrame;
        currentFrame++;
        frame.up = Controls.UP.isPressed();
        frame.left = Controls.LEFT.isPressed();
        frame.down = Controls.DOWN.isPressed();
        frame.right = Controls.RIGHT.isPressed();
        frame.jump = Controls.JUMP.isPressed();
        frame.run = Controls.RUN.isPressed();
        if (frame.equals(lastFrame)) return;
        lastFrame = frame;
        frames.add(frame);
    }
    public RecordingFrame justPressed() {
        RecordingFrame curr = pressed();
        currentFrame--;
        RecordingFrame prev = pressed();
        currentFrame++;
        if (prev == null) return curr;
        RecordingFrame just = new RecordingFrame();
        just.up = curr.up && !prev.up;
        just.left = curr.left && !prev.left;
        just.down = curr.down && !prev.down;
        just.right = curr.right && !prev.right;
        just.jump = curr.jump && !prev.jump;
        just.run = curr.run && !prev.run;
        return just;
    }
    public RecordingFrame pressed() {
        RecordingFrame current = new RecordingFrame();
        for (RecordingFrame frame : frames) {
            if (frame.frame <= currentFrame) current = frame;
            if (frame.frame > currentFrame) break;
        }
        return current;
    }
    public ObjectElement save() {
        ObjectElement element = new ObjectElement();
        ListElement frames = new ListElement();
        for (RecordingFrame frame : this.frames) {
            ObjectElement frameData = new ObjectElement();
            frameData.setBoolean("up", frame.up);
            frameData.setBoolean("left", frame.left);
            frameData.setBoolean("down", frame.down);
            frameData.setBoolean("right", frame.right);
            frameData.setBoolean("jump", frame.jump);
            frameData.setBoolean("run", frame.run);
            frameData.setInt("frame", frame.frame);
            frames.addObject(frameData);
        }
        element.setShort("level", (short)level);
        element.setList("frames", frames);
        element.setShort("length", (short)currentFrame);
        return element;
    }
    public void seek(int pos) {
        currentFrame = pos;
    }
    public void next() {
        currentFrame++;
    }
    public boolean done() {
        return currentFrame >= length;
    }
    public static class RecordingFrame {
        public boolean up;
        public boolean left;
        public boolean down;
        public boolean right;
        public boolean jump;
        public boolean run;
        public int frame;
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj instanceof RecordingFrame) {
                RecordingFrame frame = (RecordingFrame)obj;
                return up == frame.up && left == frame.left && down == frame.down && right == frame.right && jump == frame.jump && run == frame.run;
            }
            return false;
        }
        public String toString() {
            return (up ? "^" : "") + (left ? "<" : "") + (down ? "v" : "") + (right ? ">" : "") + (jump ? "j" : "") + (run ? "r" : "");
        }
    }
}
