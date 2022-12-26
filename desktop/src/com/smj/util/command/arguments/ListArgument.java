package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

import java.util.ArrayList;
import java.util.Arrays;

public class ListArgument extends CommandArgument<Integer> {
    public ArrayList<String> values;
    public ListArgument(String name, String... values) {
        super(name);
        this.values = new ArrayList<>(Arrays.asList(values));
    }
    public Integer parse(String[] string, AccessCounter counter) {
        int index = counter.access();
        try {
            return Integer.parseInt(string[index]);
        }
        catch (Exception e) {
            return values.indexOf(string[index]);
        }
    }
}
