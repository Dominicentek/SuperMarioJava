package com.smj.util.command.console;

public class StdoutConsole implements Console {
    public void log(String message) {
        System.out.println(message);
    }
    public void err(String message) {
        System.err.println(message);
    }
    public void clr() {}
}
