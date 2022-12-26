package com.smj.util.command;

import java.util.ArrayList;

public class CommandBuilder {
    private ArrayList<CommandNode> nodes = new ArrayList<>();
    public CommandBuilder addNode(CommandNode argument) {
        nodes.add(argument);
        return this;
    }
    public ArrayList<CommandNode> get() {
        return nodes;
    }
}
