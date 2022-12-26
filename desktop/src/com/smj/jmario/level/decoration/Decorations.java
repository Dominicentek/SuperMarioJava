package com.smj.jmario.level.decoration;

import java.util.ArrayList;

public class Decorations extends ArrayList<Decoration> {
    public Decoration[] array() {
        Decoration[] decorations = new Decoration[0];
        toArray(decorations);
        return decorations;
    }
}
