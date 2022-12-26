package com.smj.gui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.smj.game.options.Controls;

public class MenuControlsItem extends MenuItem {
    public String rawLabel;
    public int keycode;
    public boolean checkingForKeycode;
    public MenuControlsItemAction action;
    public MenuControlsItem(int keycode, MenuControlsItemAction action) {
        this.keycode = keycode;
        this.action = action;
    }
    public void update(Menu menu) {
        if (rawLabel == null) rawLabel = label;
        label = checkingForKeycode ? "[PRESS ANY KEY]" : rawLabel + ": " + Input.Keys.toString(keycode);
    }
    public void updateSelected(Menu menu, int index) {
        if (checkingForKeycode) {
            for (int i = 0; i < Input.Keys.MAX_KEYCODE; i++) {
                if (Gdx.input.isKeyJustPressed(i)) {
                    keycode = i;
                    checkingForKeycode = false;
                    action.keybindChanged(menu, index, this);
                    break;
                }
            }
            overrideInput();
        }
        else if (Controls.JUMP.isJustPressed()) checkingForKeycode = true;
    }
    public interface MenuControlsItemAction {
        void keybindChanged(Menu menu, int index, MenuControlsItem item);
    }
}
