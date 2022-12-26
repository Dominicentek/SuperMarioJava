package com.smj.util.command;

import java.util.HashMap;

public class CommandContext {
    public HashMap<String, Object> values = new HashMap<>();
    public <T> T get(String name) {
        return (T)values.get(name);
    }
}
