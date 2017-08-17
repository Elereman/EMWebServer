package com.elereman.webserver.console;

import java.io.PrintStream;
import java.util.List;

public class Command {
    private String command;
    private List<String> args;
    private PrintStream out;

    public Command(String command, List<String> args, PrintStream out) {
        this.command = command;
        this.args = args;
        this.out = out;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }

    public PrintStream getOutputStream() {
        return out;
    }
}
