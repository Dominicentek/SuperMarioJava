package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.smj.util.bjson.JSONParser;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.util.HashMap;

public class GameStrings {
    private static final HashMap<String, String> globalTexts = new HashMap<>();
    private static final HashMap<String, MenuText> menus = new HashMap<>();
    public static String get(String key) {
        return globalTexts.getOrDefault(key, key);
    }
    public static MenuText getMenu(String key) {
        return menus.get(key);
    }
    public static void parse() {
        ObjectElement element = JSONParser.parse(Gdx.files.internal("assets/strings/texts.json").readString());
        ObjectElement globals = element.getObject("global");
        for (String key : globals.keys()) {
            if (globals.isString(key)) {
                globalTexts.put(key, globals.getString(key));
            }
            if (globals.isList(key)) {
                String text = "";
                ListElement list = globals.getList(key);
                for (int i = 0; i < list.size(); i++) {
                    text += "\n" + list.getString(i);
                }
                globalTexts.put(key, text.substring(1));
            }
        }
        ObjectElement menus = element.getObject("menu");
        for (String key : menus.keys()) {
            ObjectElement menu = menus.getObject(key);
            String title = menu.getString("title");
            ListElement itemsList = menu.getList("options");
            String[] items = new String[itemsList.size()];
            for (int i = 0; i < itemsList.size(); i++) {
                items[i] = itemsList.getString(i);
            }
            MenuText menuText = new MenuText(title, items);
            GameStrings.menus.put(key, menuText);
        }
    }
    public static class MenuText {
        private final String title;
        private final String[] items;
        private MenuText(String title, String... items) {
            this.title = title;
            this.items = items;
        }
        public String getTitle() {
            return title;
        }
        public String getItem(int index) {
            return items[index];
        }
    }
}
