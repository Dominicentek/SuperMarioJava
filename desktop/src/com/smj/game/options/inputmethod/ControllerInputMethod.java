package com.smj.game.options.inputmethod;

import com.smj.controller.ControllerInterface;

public class ControllerInputMethod implements InputMethod {
    public boolean isJustPressed(int code) {
        return ControllerInterface.justPressed.contains(code);
    }
    public boolean isHeld(int code) {
        return ControllerInterface.held.contains(code);
    }
    public String getName(int code) {
        if ((code & (1 << 7)) != 0) {
            int dir = code & 1;
            int axis = (code & ~(1 | (1 << 7))) >>> 1;
            int stick = axis / 2;
            if (dir == ControllerInterface.DIR_NEG && axis % 2 == 1) return "Stick " + stick + " Up";
            if (dir == ControllerInterface.DIR_POS && axis % 2 == 1) return "Stick " + stick + " Down";
            if (dir == ControllerInterface.DIR_NEG) return "Stick " + stick + " Left";
            return "Stick " + stick + " Right";
        }
        return "Button " + code;
    }
    public int id() {
        return 1;
    }
}
