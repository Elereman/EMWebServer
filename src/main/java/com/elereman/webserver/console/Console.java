package com.elereman.webserver.console;

import com.elereman.webserver.console.commands.api.CommandHandler;
import com.elereman.webserver.console.commands.impl.PrintCommandHandler;
import com.elereman.webserver.console.commands.impl.StartCommandHandler;
import com.elereman.webserver.console.commands.impl.StopCommandHandler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Console {

    private static List<CommandHandler> handlers = new ArrayList<>();

    static {
        handlers.add(new PrintCommandHandler());
        handlers.add(new StartCommandHandler());
        handlers.add(new StopCommandHandler());
    }

    public void doCommand(String command, PrintStream out) {
        Command com = parseCommand(command, out);
        for (CommandHandler handler : handlers) {
            if(handler.getHandlingCommand().equals(com.getCommand())){
                if(!handler.handleCommand(com)) {
                    out.println("Use " + handler.getUseString());
                    return;
                }
                return;
            }
        }
        if(com.getCommand().equalsIgnoreCase("help")) {
            printAllCommands(out);
        } else {
            out.println("Command " + com.getCommand() + " is incorrect! Use help");
        }
    }

    private Command parseCommand(String command, PrintStream out) {
        StringTokenizer stringTokenizer = new StringTokenizer(command, " -");
        List<String> args = new ArrayList<>();
        String com = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            args.add(stringTokenizer.nextToken());
        }
        return new Command(com, args, out);
    }

    private void printAllCommands(PrintStream out) {
        for (CommandHandler handler : handlers) {
            out.println(handler.getUseString());
        }
    }
}
