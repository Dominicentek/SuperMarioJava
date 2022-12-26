package com.smj.util.command;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandLiteral implements CommandNode {
    public HashMap<String, ArrayList<CommandNode>> paths = new HashMap<>();
    public CommandLiteral addPath(String literalName, ArrayList<CommandNode> nodes) {
        paths.put(literalName, nodes);
        return this;
    }
    public CommandLiteral aliasTo(String alias, String original) {
        paths.put(alias, paths.get(original));
        return this;
    }
}
