package com.elereman.webserver.console.commands.api;

import com.elereman.webserver.console.Command;

public interface CommandHandler {
    String getHandlingCommand();

    boolean handleCommand(Command command);

    boolean isCommandCorrect(Command command);

    String getUseString();
}
