package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class IntegerArgument extends CommandArgument<Integer> {
    public IntegerArgument(String name) {
        super(name);
    }
    public Integer parse(String[] string, AccessCounter counter) {
        return Integer.parseInt(string[counter.access()]);
    }
}
