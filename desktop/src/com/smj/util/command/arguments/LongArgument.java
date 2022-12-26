package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class LongArgument extends CommandArgument<Long> {
    public LongArgument(String name) {
        super(name);
    }
    public Long parse(String[] string, AccessCounter counter) {
        return Long.parseLong(string[counter.access()]);
    }
}
