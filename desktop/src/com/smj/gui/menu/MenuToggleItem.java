package com.smj.gui.menu;

import com.smj.game.options.Controls;

public class MenuToggleItem extends MenuValueItem {
    public MenuToggleItem(MenuValueItemAction action, boolean value) {
        super(action, 0, value ? 1 : 0, 1);
    }
    public void updateSelected(Menu menu, int index) {
        super.updateSelected(menu, index);
        if (Controls.JUMP.isJustPressed()) {
            value = value == 0 ? 1 : 0;
            action.selected(menu, index, this);
        }
    }
    public String getLabel() {
        return labelRaw + ": " + (value == 0 ? "OFF" : "ON");
    }
}
