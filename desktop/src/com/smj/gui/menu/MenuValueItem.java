package com.smj.gui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.smj.game.options.Controls;

import javax.naming.ldap.Control;

public class MenuValueItem extends MenuItem {
    public MenuValueItemAction action;
    public int min;
    public int value;
    public int max;
    public MenuValueItem(MenuValueItemAction action, int min, int value, int max) {
        this.action = action;
        if (min > max) throw new RuntimeException("Min is bigger than max");
        if (value > max) value = max;
        if (value < min) value = min;
        this.min = min;
        this.value = value;
        this.max = max;
    }
    public void update(Menu menu) {
        right = getRight();
    }
    public void updateSelected(Menu menu, int index) {
        int prevValue = value;
        if (Controls.LEFT.isJustPressed()) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) value = min;
            else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) value -= 10;
            else value--;
        }
        if (Controls.RIGHT.isJustPressed()) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) value = max;
            else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) value += 10;
            else value++;
        }
        if (value < min) value = min;
        if (value > max) value = max;
        if (prevValue != value) action.selected(menu, index, this);
    }
    public String getRight() {
        return "" + value;
    }
    public interface MenuValueItemAction {
        void selected(Menu menu, int index, MenuValueItem item);
    }
}
