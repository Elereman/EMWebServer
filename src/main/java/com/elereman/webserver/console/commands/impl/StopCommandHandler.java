package com.elereman.webserver.console.commands.impl;

import com.elereman.webserver.console.Command;
import com.elereman.webserver.console.commands.api.CommandHandler;
import com.elereman.webserver.net.WebServer;

/**
 * Created by Elereman on 27.07.2017.
 */
public class StopCommandHandler implements CommandHandler {
    private String handlingCommand = "stop";
    private String useString = "stop";

    @Override
    public String getHandlingCommand() {
        return handlingCommand;
    }

    @Override
    public boolean handleCommand(Command command) {
        if (isCommandCorrect(command)) {
            WebServer.stopServer();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCommandCorrect(Command command) {
        if (command.getArgs().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getUseString() {
        return useString;
    }
}
