package com.smj.util.command;

public interface CommandExecution extends CommandNode {
    void run(CommandContext context);
}
