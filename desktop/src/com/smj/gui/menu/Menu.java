package com.smj.gui.menu;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.options.Controls;
import com.smj.gui.font.Font;
import com.smj.util.*;

import java.util.ArrayList;
import java.util.Stack;

public class Menu {
    public static Stack<Menu> menuHistory = new Stack<>();
    public static Menu currentMenu = null;
    public static final String[] challengeDescriptions = FileLoader.read("strings/challengedesc.txt").asString().split("\n");
    public MenuItem[] items;
    public int selectedIndex = 0;
    public String title;
    public int offsetY = 0;
    public double selector = 0;
    public double interpolationRaw = 0;
    public double from = 0;
    public double to = 0;
    public boolean interpolationInvert = false;
    public Menu nextMenu;
    public int backButton = -1;
    public boolean noScroll = false;
    public Menu(MenuItem... items) {
        this.items = items;
    }
    public void render(Renderer renderer) {
        double selector = (noScroll ? 0 : this.selector);
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
        if (!noScroll) y += selector * 24;
        if (this == Menus.CHALLENGE_CONFIRM) renderChallengeDescription(renderer, y, offset);
        int rightX = x + width;
        Font.render(renderer, Main.WIDTH / 2 - Font.stringWidth(title) / 2 + offset, (int)(y + 8 + ((-1 - selector) * 24)), title);
        for (int i = 0; i < items.length; i++) {
            Font.render(renderer, x + 12, (int)(y + 8 + ((i - selector) * 24)), items[i].label);
            Font.render(renderer, rightX - 12 - Font.stringWidth(items[i].right), (int)(y + 8 + ((i - selector) * 24)), items[i].right);
        }
        if (noScroll) y += this.selector * 24;
        renderer.rect(x, y, width, 1);
        renderer.rect(x, y, 1, height);
        renderer.rect(x, y + height - 1, width, 1);
        renderer.rect(x + width - 1, y, 1, height);
        if (currentMenu == Menus.MAIN) renderer.draw(TextureLoader.get("images/logo.png"), Main.WIDTH / 2 - 168 / 2 + offset, 24);
    }
    public void renderChallengeDescription(Renderer renderer, int y, int offset) {
        ArrayList<String> description = new ArrayList<>();
        String[] splitDescription = challengeDescriptions[Game.currentChallengeIndex].split(" ");
        int length = 0;
        String line = "";
        for (int i = 0; i < splitDescription.length; i++) {
            length += Font.stringWidth(splitDescription[i] + 1);
            if (length > 32 * 8 && !line.isEmpty()) {
                description.add(line.substring(0, line.length() - 1));
                length = 0;
                line = "";
            }
            line += splitDescription[i] + " ";
        }
        if (!line.isEmpty()) description.add(line.substring(0, line.length() - 1));
        Font.render(renderer, Main.WIDTH / 2 - Font.stringWidth(Game.currentChallengeName) / 2 + offset, y - 108 - description.size() * 12, Game.currentChallengeName);
        int medal = Game.currentChallenge.highScore == null ? 0 : Game.currentChallenge.medals.medal(Game.currentChallenge.highScore);
        String string = Game.currentChallenge.event.getString(Game.currentChallenge.medals, Game.currentChallenge.highScore);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + offset, y - 92 - description.size() * 12, 8 + Font.stringWidth(string), 16);
        renderer.setColor(0xFFFFFFFF);
        Font.render(renderer, Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + 4 + offset, y - 88 - description.size() * 12, string);
        for (int i = 0; i < description.size(); i++) {
            Font.render(renderer, Main.WIDTH / 2 - Font.stringWidth(description.get(i)) / 2 + offset, y - 68 - description.size() * 12 + i * 12, description.get(i));
        }
        string = Game.currentChallenge.event.getString(Game.currentChallenge.medals, Game.currentChallenge.medals.gold);
        renderer.setColor(0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + offset, y - 62, 8 + Font.stringWidth(string), 16);
        renderer.setColor(0xFFFFFFFF);
        Font.render(renderer, Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + 4 + offset, y - 58, string);
        string = Game.currentChallenge.event.getString(Game.currentChallenge.medals, Game.currentChallenge.medals.silver);
        renderer.setColor(0xDFDFDFFF);
        renderer.rect(Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + offset, y - 42, 8 + Font.stringWidth(string), 16);
        renderer.setColor(0xFFFFFFFF);
        Font.render(renderer, Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + 4 + offset, y - 38, string);
        string = Game.currentChallenge.event.getString(Game.currentChallenge.medals, Game.currentChallenge.medals.bronze);
        renderer.setColor(0x7F7F00FF);
        renderer.rect(Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + offset, y - 22, 8 + Font.stringWidth(string), 16);
        renderer.setColor(0xFFFFFFFF);
        Font.render(renderer, Main.WIDTH / 2 - (8 + Font.stringWidth(string)) / 2 + 4 + offset, y - 18, string);
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
    public Menu noScroll() {
        noScroll = true;
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
