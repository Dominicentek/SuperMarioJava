package com.smj.game.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.Readable;
import com.smj.util.Saveable;
import com.smj.util.bjson.ObjectElement;

public class Options implements Saveable {
    public boolean hiddenHUD = false;
    public boolean saveFileScreenshot = true;
    public boolean saveClipScreenshot = false;
    public boolean stereoSound = true;
    public float soundVolume = 1f;
    public float musicVolume = 1f;
    public boolean speedrunTimer = false;
    public boolean crtShader = false;
    public Options() {}
    public Options(ObjectElement element) {
        hiddenHUD = element.getBoolean("hiddenHUD");
        saveFileScreenshot = element.getBoolean("fileScreenshot");
        saveClipScreenshot = element.getBoolean("clipScreenshot");
        stereoSound = element.getBoolean("stereo");
        soundVolume = element.getFloat("sound");
        musicVolume = element.getFloat("music");
        speedrunTimer = element.getBoolean("speedrun");
        crtShader = element.getBoolean("crt");
        ObjectElement input = element.getObject("input");
        for (String inputName : input.keys()) {
            Controls.valueOf(inputName).keybind = Byte.toUnsignedInt(input.getByte(inputName));
        }
        HUDLayout.read(element.getObject("hud"));
    }
    public ObjectElement save() {
        ObjectElement element = new ObjectElement();
        element.setBoolean("hiddenHUD", hiddenHUD);
        element.setBoolean("fileScreenshot", saveFileScreenshot);
        element.setBoolean("clipScreenshot", saveClipScreenshot);
        element.setBoolean("stereo", stereoSound);
        element.setFloat("sound", soundVolume);
        element.setFloat("music", musicVolume);
        element.setBoolean("speedrun", speedrunTimer);
        element.setBoolean("crt", crtShader);
        ObjectElement input = new ObjectElement();
        for (Controls control : Controls.values()) {
            input.setByte(control.name(), (byte)control.keybind);
        }
        element.setObject("input", input);
        ObjectElement hud = new ObjectElement();
        HUDLayout.store(hud);
        element.setObject("hud", hud);
        return element;
    }
    public static Options load() {
        Options options;
        FileHandle file = Gdx.files.local("options.dat");
        if (file.exists()) options = Readable.read(file, Options.class);
        else options = new Options();
        Saveable.save(options, file);
        return options;
    }
}
