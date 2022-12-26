package com.smj.util.command.console;

public class TextConsole implements Console {
    public String data = "";
    public TextConsole() {
        log("Welcome to the SMJ console");
        log("");
        log("Look up the documentation in the GitHub README");
        log("For all the commands");
        log("");
    }
    public void log(String line) {
        data += line + "\n";
    }
    public void err(String line) {
        data += "{E}" + line + "\n";
    }
    public void clr() {
        data = "";
    }
}
