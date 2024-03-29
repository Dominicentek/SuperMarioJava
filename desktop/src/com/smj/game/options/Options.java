package com.smj.game.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.smj.game.options.inputmethod.InputMethod;
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
    public Options() {}
    public Options(ObjectElement element) {
        hiddenHUD = element.getBoolean("hiddenHUD");
        saveFileScreenshot = element.getBoolean("fileScreenshot");
        saveClipScreenshot = element.getBoolean("clipScreenshot");
        stereoSound = element.getBoolean("stereo");
        soundVolume = element.getFloat("sound");
        musicVolume = element.getFloat("music");
        speedrunTimer = element.getBoolean("speedrun");
        ObjectElement input = element.getObject("input");
        for (String inputName : input.keys()) {
            Controls control = Controls.valueOf(inputName);
            if (input.isByte(inputName)) {
                control.inputMethod = InputMethod.KEYBOARD;
                control.keybind = Byte.toUnsignedInt(input.getByte(inputName));
                continue;
            }
            ObjectElement controlElement = input.getObject(inputName);
            control.inputMethod = InputMethod.BY_ID[Byte.toUnsignedInt(controlElement.getByte("input_method"))];
            control.keybind = Byte.toUnsignedInt(controlElement.getByte("code"));
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
        ObjectElement input = new ObjectElement();
        for (Controls control : Controls.values()) {
            ObjectElement controlElement = new ObjectElement();
            controlElement.setByte("code", (byte)control.keybind);
            controlElement.setByte("input_method", (byte)control.inputMethod.id());
            input.setObject(control.name(), controlElement);
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
        if (file.exists()) options = Readable.read(file.readBytes(), Options.class);
        else options = new Options();
        Saveable.save(options, file);
        return options;
    }
}
