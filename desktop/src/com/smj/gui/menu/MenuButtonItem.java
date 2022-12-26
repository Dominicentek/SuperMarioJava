package com.smj.gui.menu;

import com.smj.game.options.Controls;

public class MenuButtonItem extends MenuItem {
    public MenuButtonItemAction action;
    public MenuButtonItem(MenuButtonItemAction action) {
        this.action = action;
    }
    public void update(Menu menu) {}
    public void updateSelected(Menu menu, int index) {
        if (Controls.JUMP.isJustPressed()) action.selected(menu, index, this);
    }
    public interface MenuButtonItemAction {
        void selected(Menu menu, int index, MenuButtonItem item);
    }
}
