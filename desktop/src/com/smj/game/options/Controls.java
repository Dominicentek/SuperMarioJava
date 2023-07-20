package com.smj.game.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    Controls(int keybind) {
        this.keybind = keybind;
    }
    public boolean isJustPressed() {
        return Gdx.input.isKeyJustPressed(keybind);
    }
    public boolean isPressed() {
        return Gdx.input.isKeyPressed(keybind);
    }
    public String toString() {
        return Input.Keys.toString(keybind);
    }
    public static MenuControlsItem[] getMenuItems() {
        Controls[] values = values();
        MenuControlsItem[] items = new MenuControlsItem[values.length];
        for (int i = 0; i < items.length; i++) {
            final int index = i;
            items[i] = new MenuControlsItem(values[i].keybind, (menu, selectedIndex, item) -> {
                values[index].keybind = item.keycode;
            });
        }
        return items;
    }
}
