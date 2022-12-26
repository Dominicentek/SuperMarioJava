package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

public class ByteArgument extends CommandArgument<Byte> {
    public ByteArgument(String name) {
        super(name);
    }
    public Byte parse(String[] string, AccessCounter counter) {
        return Byte.parseByte(string[counter.access()]);
    }
}
