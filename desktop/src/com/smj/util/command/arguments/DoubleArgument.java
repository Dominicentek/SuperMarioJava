package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class DoubleArgument extends CommandArgument<Double> {
    public DoubleArgument(String name) {
        super(name);
    }
    public Double parse(String[] string, AccessCounter counter) {
        return Double.parseDouble(string[counter.access()]);
    }
}
