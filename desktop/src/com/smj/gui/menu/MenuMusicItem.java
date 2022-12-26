package com.smj.gui.menu;

import com.smj.game.Game;
import com.smj.util.SMJMusic;

public class MenuMusicItem extends MenuValueItem {
    public boolean selected = false;
    public boolean previouslySelected = false;
    public MenuMusicItem(MenuValueItemAction action, int min, int value, int max) {
        super(action, min, value, max);
    }
    public void update(Menu menu) {
        super.update(menu);
        if (selected && !previouslySelected && Game.pauseMenuOpen) SMJMusic.resume();
        if (!selected && previouslySelected && Game.pauseMenuOpen) SMJMusic.pause();
        previouslySelected = selected;
        selected = false;
    }
    public void updateSelected(Menu menu, int index) {
        super.updateSelected(menu, index);
        selected = true;
    }
}
