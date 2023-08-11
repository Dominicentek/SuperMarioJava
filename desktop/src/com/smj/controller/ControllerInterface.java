package com.smj.controller;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.smj.gui.menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ControllerInterface extends ControllerAdapter {
    public static final int DIR_NEG = 0;
    public static final int DIR_POS = 1;
    public static final HashSet<Integer> held = new HashSet<>();
    public static final HashSet<Integer> justPressed = new HashSet<>();
    private static final HashSet<Integer> previouslyHeld = new HashSet<>();
    private static final HashMap<Integer, Float> stick = new HashMap<>();
    public static float stickThreshold = 0.15f;
    public static void update() {
        justPressed.clear();
        for (Map.Entry<Integer, Float> entry : stick.entrySet()) {
            int buttonID;
            boolean remove;
            if (entry.getValue() == 0) continue;
            if (entry.getValue() > 0) {
                remove = entry.getValue() < stickThreshold;
                buttonID = stickToButton(entry.getKey(), DIR_POS);
            }
            else {
                remove = entry.getValue() > -stickThreshold;
                buttonID = stickToButton(entry.getKey(), DIR_NEG);
            }
            if (remove) held.remove(buttonID);
            else held.add(buttonID);
        }
        for (int code : held) {
            if (!previouslyHeld.contains(code)) justPressed.add(code);
        }
        previouslyHeld.clear();
        previouslyHeld.addAll(held);

    }
    public boolean buttonDown(Controller controller, int buttonIndex) {
        held.add(buttonIndex);
        return true;
    }
    public boolean buttonUp(Controller controller, int buttonIndex) {
        held.remove(buttonIndex);
        return true;
    }
    public boolean axisMoved(Controller controller, int axisIndex, float value) {
        stick.put(axisIndex, value);
        return true;
    }
    public void connected(Controller controller) {
        System.out.println("Controller connected");
    }
    public void disconnected(Controller controller) {
        System.out.println("Controller disconnected");
    }
    public static int stickToButton(int axis, int direction) {
        return (1 << 7) | (axis << 1) | direction;
    }
}
