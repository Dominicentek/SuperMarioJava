package com.smj.util;

public class StackTrace extends Exception {
    public static void trace() {
        try {
            throw new StackTrace();
        }
        catch (StackTrace e) {
            e.printStackTrace(System.out);
        }
    }
}
