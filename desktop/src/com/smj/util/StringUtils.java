package com.smj.util;

public class StringUtils {
    public static String repeat(String str, int amount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
