package com.smj.util;

public class ArrayStringifier {
    @SafeVarargs
    public static <T> String[] modify(Stringifier<T> stringifier, T... array) {
        String[] strings = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            strings[i] = stringifier.stringify(array[i]);
        }
        return strings;
    }
}
