package com.elereman.webserver.commands;

import com.elereman.webserver.console.Command;
import com.elereman.webserver.console.commands.api.CommandHandler;
import com.elereman.webserver.console.commands.impl.StopCommandHandler;
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
public class TestStopCommand {
    private static CommandHandler stopCommand;
    private static Command command;
    private static PrintStream out;

    @BeforeClass
    public static void beforeClass() {
        stopCommand = new StopCommandHandler();
        out = mock(PrintStream.class);
        command = new Command("stop", Collections.emptyList(), out);
    }

    @Test
    public void testHandleCommand() {
        stopCommand.handleCommand(command);
    }

    @Test
    public void testGetHandlingCommand() {
        assertEquals("stop", stopCommand.getHandlingCommand());
    }

    @Test
    public void testGetUseString() {
        assertEquals("stop", stopCommand.getUseString());
    }

    @Test
    public void testIsCommandCorrect() {
        assertEquals(true, stopCommand.isCommandCorrect(command));
    }

    @Test
    public void testIsCommandInCorrect() {
        List<String> list = new ArrayList<>();
        list.add("sse");
        Command incorrectCommand = new Command("incorrect", list, out);
        assertEquals(false, stopCommand.isCommandCorrect(incorrectCommand));
    }

    @Test
    public void testCantHandleCommand() {
        List<String> list = new ArrayList<>();
        list.add("sse");
        Command incorrectCommand = new Command("incorrect", list, out);
        assertEquals(false, stopCommand.handleCommand(incorrectCommand));
    }
}
