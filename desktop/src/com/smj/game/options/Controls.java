package com.smj.game.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.smj.controller.ControllerInterface;
import com.smj.game.options.inputmethod.ControllerInputMethod;
import com.smj.game.options.inputmethod.InputMethod;
import com.smj.game.options.inputmethod.KeyboardInputMethod;
import com.smj.gui.menu.MenuControlsItem;

public enum Controls {
    UP(Input.Keys.W),
    LEFT(Input.Keys.A),
    DOWN(Input.Keys.S),
    RIGHT(Input.Keys.D),
    LOOK_UP(Input.Keys.UP),
    LOOK_LEFT(Input.Keys.LEFT),
    LOOK_DOWN(Input.Keys.DOWN),
    LOOK_RIGHT(Input.Keys.RIGHT),
    JUMP(Input.Keys.SPACE),
    RUN(Input.Keys.SHIFT_LEFT),
    PAUSE(Input.Keys.ESCAPE),
    FULLSCREEN(Input.Keys.F11),
    TOGGLE_HUD(Input.Keys.F1),
    SCREENSHOT(Input.Keys.F2),
    CONSOLE(Input.Keys.F3);
    public int keybind;
    public InputMethod inputMethod = InputMethod.KEYBOARD;
    Controls(int keybind) {
        this.keybind = keybind;
    }
    public boolean isJustPressed() {
        return inputMethod.isJustPressed(keybind);
    }
    public boolean isPressed() {
        return inputMethod.isHeld(keybind);
    }
    public String toString() {
        return inputMethod.getName(keybind);
    }
    public static MenuControlsItem[] getMenuItems() {
        Controls[] values = values();
        MenuControlsItem[] items = new MenuControlsItem[values.length];
        for (int i = 0; i < items.length; i++) {
            final int index = i;
            items[i] = new MenuControlsItem(values[i], values[i].keybind, (menu, selectedIndex, item) -> {
                values[index].keybind = item.keycode;
                values[index].inputMethod = item.inputMethod;
            });
        }
        return items;
    }
}
