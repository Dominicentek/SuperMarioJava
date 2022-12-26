package com.smj.util;

public class AccessCounter {
    private int value;
    public AccessCounter(int value) {
        this.value = value;
    }
    public int access() {
        int x = value;
        value++;
        return x;
    }
    public int get() {
        return value;
    }
}
