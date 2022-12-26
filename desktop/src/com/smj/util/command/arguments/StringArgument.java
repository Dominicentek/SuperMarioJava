package com.smj.util.command.arguments;

import com.smj.util.AccessCounter;

import java.io.StringReader;
import java.util.Properties;

public class StringArgument extends CommandArgument<String> {
    public StringArgument(String name) {
        super(name);
    }
    public String parse(String[] string, AccessCounter counter) {
        String word = string[counter.access()];
        if (word.startsWith("\"")) {
            StringBuilder str = new StringBuilder(word);
            while (counter.get() < string.length) {
                String w = string[counter.access()];
                str.append(" ").append("\"");
                if (w.endsWith("\"")) break;
            }
            try {
                Properties properties = new Properties();
                properties.load(new StringReader("x=" + str));
                return properties.getProperty("x");
            }
            catch (Exception e) {
                return str.toString();
            }
        }
        return word;
    }
}
