package com.smj.gui.menu;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.options.Controls;
import com.smj.gui.font.Font;
import com.smj.util.GameStrings;
import com.smj.util.RNG;
import com.smj.util.Recording;
import com.smj.util.Renderer;

import java.util.Stack;

public class Menu {
    public static Stack<Menu> menuHistory = new Stack<>();
    public static Menu currentMenu = null;
    private MenuItem[] items;
    private int selectedIndex = 0;
    public String title;
    public int offsetX = 0;
    public int offsetY = 0;
    public Menu(MenuItem... items) {
        this.items = items;
    }
    public void render(Renderer renderer) {
        int width = Font.stringWidth(title);
        for (MenuItem item : items) {
            width = Math.max(width, Font.stringWidth(item.label));
        }
        width += 24;
        int height = (items.length + (title.isEmpty() ? 0 : 2)) * 12 + 4;
        int x = Main.WIDTH / 2 - width / 2 + offsetX;
        int y = Main.HEIGHT / 2 - height / 2 + offsetY;
        renderer.rect(x, y, width, height, 0x0000007F);
        renderer.setColor(0xFFFFFFFF);
        x += 4;
        y += 4;
        renderer.drawString(title, x, y);
        y += title.isEmpty() ? 0 : 24;
        for (int i = 0; i < items.length; i++) {
            MenuItem item = items[i];
            String label = (i == selectedIndex ? "> " : "  ") + item.label;
            renderer.drawString(label, x, y);
            y += 12;
        }
    }
    public void update() {
        for (MenuItem item : items) {
            item.update(this);
        }
        items[selectedIndex].updateSelected(this, selectedIndex);
        if (!items[selectedIndex].overriddenInput()) {
            if (Controls.DOWN.isJustPressed() && selectedIndex != items.length - 1) selectedIndex++;
            if (Controls.UP.isJustPressed() && selectedIndex != 0) selectedIndex--;
        }
        items[selectedIndex].overrideInput(false);
        if (currentMenu == Menus.MAIN && Game.playback == null) {
            Game.demoTimeout--;
            if (Game.demoTimeout == 0) {
                Game.playbackRecording(RNG.choose(Recording.DEMOS));
                Game.demoTimeout = 600;
            }
        }
        else Game.demoTimeout = 600;
    }
    public Menu offset(int x, int y) {
        offsetX = x;
        offsetY = y;
        return this;
    }
    public Menu text(String menuID) {
        title = GameStrings.getMenu(menuID).getTitle();
        for (int i = 0; i < items.length; i++) {
            items[i].label = GameStrings.getMenu(menuID).getItem(i);
        }
        return this;
    }
    public static void loadMenu(Menu menu) {
        currentMenu = menu;
        if (menu != null) menuHistory.push(menu);
        else menuHistory.clear();
        Main.menu = menu;
    }
    public static void goBack() {
        if (menuHistory.size() < 2) loadMenu(null);
        else {
            menuHistory.pop();
            loadMenu(menuHistory.pop());
        }
    }
}
