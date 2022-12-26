package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class ShortArgument extends CommandArgument<Short> {
    public ShortArgument(String name) {
        super(name);
    }
    public Short parse(String[] string, AccessCounter counter) {
        return Short.parseShort(string[counter.access()]);
    }
}
