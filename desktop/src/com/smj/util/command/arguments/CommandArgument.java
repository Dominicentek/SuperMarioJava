package com.smj.util.command.arguments;

import com.smj.util.command.CommandNode;
import com.smj.util.AccessCounter;

public abstract class CommandArgument<T> implements CommandNode {
    public String name;
    public CommandArgument(String name) {
        this.name = name;
    }
    public abstract T parse(String[] string, AccessCounter counter);
}
