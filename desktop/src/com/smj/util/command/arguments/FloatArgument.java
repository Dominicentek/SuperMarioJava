package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class FloatArgument extends CommandArgument<Float> {
    public FloatArgument(String name) {
        super(name);
    }
    public Float parse(String[] string, AccessCounter counter) {
        return Float.parseFloat(string[counter.access()]);
    }
}
