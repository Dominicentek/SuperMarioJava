package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class BooleanArgument extends CommandArgument<Boolean> {
    public BooleanArgument(String name) {
        super(name);
    }
    public Boolean parse(String[] string, AccessCounter counter) {
        return Boolean.parseBoolean(string[counter.access()]);
    }
}
