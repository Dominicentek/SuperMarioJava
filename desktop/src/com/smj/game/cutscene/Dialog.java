package com.smj.game.cutscene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.smj.util.TextureLoader;
import com.smj.util.bjson.JSONParser;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.util.HashMap;

public class Dialog {
    private static boolean parsed = false;
    public static final HashMap<String, Texture> icons = new HashMap<>();
    public static final HashMap<String, Dialog[]> dialogs = new HashMap<>();
    public String text;
    public Texture icon;
    public int progress;
    public Dialog(Texture icon, String text) {
        this.icon = icon;
        this.text = text;
    }
    public static void parse() {
        if (parsed) return;
        parsed = true;
        ObjectElement element = JSONParser.parse(Gdx.files.internal("assets/strings/cutscenedialog.json").readString());
        for (String key : element.keys()) {
            ListElement dialogsList = element.getList(key);
            Dialog[] dialogs = new Dialog[dialogsList.size()];
            for (int i = 0; i < dialogsList.size(); i++) {
                ObjectElement dialogData = dialogsList.getObject(i);
                Dialog dialog = new Dialog(icons.get(dialogData.getString("icon")), dialogData.getString("text"));
                dialogs[i] = dialog;
            }
            Dialog.dialogs.put(key, dialogs);
        }
    }
    public Dialog copy() {
        return new Dialog(icon, text);
    }
    static {
        icons.put("mario", TextureLoader.get(Gdx.files.internal("assets/images/icons/mario.png")));
        icons.put("peach", TextureLoader.get(Gdx.files.internal("assets/images/icons/peach.png")));
        icons.put("bowser", TextureLoader.get(Gdx.files.internal("assets/images/icons/bowser.png")));
        icons.put("toad", TextureLoader.get(Gdx.files.internal("assets/images/icons/toad.png")));
    }
}
