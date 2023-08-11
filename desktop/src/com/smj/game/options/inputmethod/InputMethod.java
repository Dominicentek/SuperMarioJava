package com.smj.game.options.inputmethod;

public interface InputMethod {
    InputMethod KEYBOARD = new KeyboardInputMethod();
    InputMethod CONTROLLER = new ControllerInputMethod();
    InputMethod[] BY_ID = { KEYBOARD, CONTROLLER };
    boolean isJustPressed(int code);
    boolean isHeld(int code);
    String getName(int code);
    int id();
}
