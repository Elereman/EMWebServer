package com.elereman.webserver.commands;

import com.elereman.webserver.console.Command;
import com.elereman.webserver.console.commands.api.CommandHandler;
import com.elereman.webserver.console.commands.impl.StartCommandHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Elereman on 14.08.2017.
 */
public class TestStartCommand {
    private static CommandHandler startCommand;
    private static Command command;
    private static PrintStream out;

    @BeforeClass
    public static void beforeClass() {
        startCommand = new StartCommandHandler();
        out = mock(PrintStream.class);
        command = new Command("start", Collections.emptyList(), out);
    }

    @Test
    public void testHandleCommand() {
        startCommand.handleCommand(command);
    }

    @Test
    public void testGetHandlingCommand() {
        assertEquals("start", startCommand.getHandlingCommand());
    }

    @Test
    public void testGetUseString() {
        assertEquals("start", startCommand.getUseString());
    }

    @Test
    public void testIsCommandCorrect() {
        assertEquals(true, startCommand.isCommandCorrect(command));
    }

    @Test
    public void testIsCommandInCorrect() {
        List<String> list = new ArrayList<>();
        list.add("sse");
        Command incorrectCommand = new Command("incorrect", list, out);
        assertEquals(false, startCommand.isCommandCorrect(incorrectCommand));
    }

    @Test
    public void testCantHandleCommand() {
        List<String> list = new ArrayList<>();
        list.add("sse");
        Command incorrectCommand = new Command("incorrect", list, out);
        assertEquals(false, startCommand.handleCommand(incorrectCommand));
    }
}
