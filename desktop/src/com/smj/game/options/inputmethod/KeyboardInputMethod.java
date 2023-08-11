package com.smj.game.options.inputmethod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardInputMethod implements InputMethod {
    public boolean isJustPressed(int code) {
        return Gdx.input.isKeyJustPressed(code);
    }
    public boolean isHeld(int code) {
        return Gdx.input.isKeyPressed(code);
    }
    public String getName(int code) {
        return Input.Keys.toString(code);
    }
    public int id() {
        return 0;
    }
}
