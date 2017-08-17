package com.elereman.webserver.console.commands.impl;


import com.elereman.webserver.console.Command;
import com.elereman.webserver.console.commands.api.CommandHandler;

import java.io.PrintStream;

public class PrintCommandHandler implements CommandHandler {
    private String handlingCommand = "print";
    private String useString = "print [arg]";

    @Override
    public String getHandlingCommand() {
        return handlingCommand;
    }

    @Override
    public boolean handleCommand(Command command) {
        if(isCommandCorrect(command)) {
            PrintStream out = command.getOutputStream();
            out.println(command.getArgs().get(0));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCommandCorrect(Command command) {
        if(command == null || command.getArgs() == null || command.getArgs().size() == 0 || command.getArgs().size() > 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getUseString() {
        return useString;
    }
}
