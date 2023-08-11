package com.smj.gui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.smj.controller.ControllerInterface;
import com.smj.game.options.Controls;
import com.smj.game.options.inputmethod.ControllerInputMethod;
import com.smj.game.options.inputmethod.InputMethod;

public class MenuControlsItem extends MenuItem {
    public int keycode;
    public boolean checkingForKeycode;
    public MenuControlsItemAction action;
    public Controls bind;
    public InputMethod inputMethod;
    public MenuControlsItem(Controls bind, int keycode, MenuControlsItemAction action) {
        this.bind = bind;
        this.keycode = keycode;
        this.action = action;
    }
    public void update(Menu menu) {
        right = checkingForKeycode ? "[PRESS ANY KEY]" : bind.toString();
    }
    public void updateSelected(Menu menu, int index) {
        if (checkingForKeycode) {
            for (int i = 0; i < Input.Keys.MAX_KEYCODE; i++) {
                if (Gdx.input.isKeyJustPressed(i)) {
                    keycode = i;
                    checkingForKeycode = false;
                    inputMethod = InputMethod.KEYBOARD;
                    action.keybindChanged(menu, index, this);
                    break;
                }
            }
            for (int code : ControllerInterface.justPressed) {
                keycode = code;
                checkingForKeycode = false;
                inputMethod = InputMethod.CONTROLLER;
                action.keybindChanged(menu, index, this);
                break;
            }
            overrideInput();
        }
        else if (Controls.JUMP.isJustPressed() && !menu.inputDisabled) checkingForKeycode = true;
    }
    public interface MenuControlsItemAction {
        void keybindChanged(Menu menu, int index, MenuControlsItem item);
    }
}
