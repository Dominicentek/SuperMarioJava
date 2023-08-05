package com.smj.gui.menu;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.options.Controls;
import com.smj.gui.font.Font;
import com.smj.util.GameStrings;
import com.smj.util.RNG;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

import java.util.Stack;

public class Menu {
    public static Stack<Menu> menuHistory = new Stack<>();
    public static Menu currentMenu = null;
    private MenuItem[] items;
    private int selectedIndex = 0;
    public String title;
    public int offsetY = 0;
    public double selector = 0;
    public double interpolationRaw = 0;
    public double from = 0;
    public double to = 0;
    public boolean interpolationInvert = false;
    public Menu nextMenu;
    public int backButton = -1;
    public Menu(MenuItem... items) {
        this.items = items;
    }
    public void render(Renderer renderer) {
        double interpolation = 1;
        if (interpolationRaw < 1) {
            interpolationRaw += 0.1;
            if (interpolationRaw > 1) interpolationRaw = 1;
            else interpolation = interpolationInvert ? 1 - (1 - interpolationRaw) * (1 - interpolationRaw) : interpolationRaw * interpolationRaw;
        }
        if (interpolationRaw == 1 && nextMenu != null) loadMenu(nextMenu, true);
        int width = (int)(Main.WIDTH * 0.7);
        int offset = (int)(Main.WIDTH * (interpolation * (to - from) + from));
        int x = Main.WIDTH / 2 - width / 2 + offset;
        int y = Main.HEIGHT / 2 + 24 / 2 + offsetY - (int)(((title.isEmpty() ? 0 : 1) + selector) * 24);
        renderer.rect(x, 0, width, Main.HEIGHT, 0x0000007F);
        renderer.setColor(0xFFFFFFFF);
        width -= 24;
        int height = 24;
        x += 12;
        y += selector * 24;
        int rightX = x + width;
        Font.render(renderer, Main.WIDTH / 2 - Font.stringWidth(title) / 2 + offset, (int)(y + 8 + ((-1 - selector) * 24)), title);
        for (int i = 0; i < items.length; i++) {
            Font.render(renderer, x + 12, (int)(y + 8 + ((i - selector) * 24)), items[i].label);
            Font.render(renderer, rightX - 12 - Font.stringWidth(items[i].right), (int)(y + 8 + ((i - selector) * 24)), items[i].right);
        }
        renderer.rect(x, y, width, 1);
        renderer.rect(x, y, 1, height);
        renderer.rect(x, y + height - 1, width, 1);
        renderer.rect(x + width - 1, y, 1, height);
        if (currentMenu == Menus.MAIN) renderer.draw(TextureLoader.get("images/logo.png"), Main.WIDTH / 2 - 168 / 2 + offset, 24);
    }
    public void update() {
        for (MenuItem item : items) {
            item.update(this);
        }
        items[selectedIndex].updateSelected(this, selectedIndex);
        if (!items[selectedIndex].overriddenInput()) {
            if (Controls.DOWN.isJustPressed() && selectedIndex != items.length - 1) selectedIndex++;
            if (Controls.UP.isJustPressed() && selectedIndex != 0) selectedIndex--;
            if (Controls.RUN.isJustPressed() && backButton != -1) ((MenuButtonItem)items[backButton]).action.selected(this, backButton, (MenuButtonItem)items[backButton]);
        }
        selector += (selectedIndex - selector) * 0.2;
        items[selectedIndex].overrideInput(false);
    }
    public Menu offset(int y) {
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
    public Menu backButton(int index) {
        backButton = index;
        return this;
    }
    public static void loadMenu(Menu menu) {
        loadMenu(menu, false);
    }
    public static void loadMenu(Menu menu, boolean animated) {
        currentMenu = menu;
        if (menu != null) {
            if (!animated) menu.interpolationRaw = 1;
            menu.nextMenu = null;
            menuHistory.push(menu);
        }
        else menuHistory.clear();
    }
    public static void switchMenu(Menu menu) {
        switchMenu(menu, false);
    }
    public static void switchMenu(Menu menu, boolean back) {
        if (currentMenu == null) {
            loadMenu(menu);
            return;
        }
        currentMenu.interpolationRaw = 0;
        currentMenu.from = 0;
        currentMenu.to = back ? 1 : -1;
        currentMenu.interpolationInvert = false;
        currentMenu.nextMenu = menu;
        menu.interpolationRaw = 0;
        menu.from = back ? -1 : 1;
        menu.to = 0;
        menu.interpolationInvert = true;
    }
    public static void goBack() {
        if (menuHistory.size() < 2) loadMenu(null);
        else {
            menuHistory.pop();
            switchMenu(menuHistory.pop(), true);
        }
    }
}
