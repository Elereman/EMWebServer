package com.elereman.webserver.commands;

import com.elereman.webserver.console.Command;
import com.elereman.webserver.console.commands.api.CommandHandler;
import com.elereman.webserver.console.commands.impl.PrintCommandHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Elereman on 14.08.2017.
 */
public class TestPrintCommand {
    private static CommandHandler printCommand;
    private static Command command;
    private static PrintStream out;

    @BeforeClass
    public static void beforeClass() {
        printCommand = new PrintCommandHandler();
        List<String> args = new ArrayList<>();
        args.add("a");
        out = mock(PrintStream.class);
        command = new Command("print", args, out);
    }

    @Test
    public void testHandleCommand() {
        printCommand.handleCommand(command);
        verify(out).println("a");
    }

    @Test
    public void testGetHandlingCommand() {
        assertEquals("print", printCommand.getHandlingCommand());
    }

    @Test
    public void testGetUseString() {
        assertEquals("print [arg]", printCommand.getUseString());
    }

    @Test
    public void testIsCommandCorrect() {
        assertEquals(true, printCommand.isCommandCorrect(command));
    }

    @Test
    public void testIsCommandInCorrect() {
        Command incorrectCommand = new Command("print", Collections.emptyList(), out);
        assertEquals(false, printCommand.isCommandCorrect(incorrectCommand));
    }

    @Test
    public void testCantHandleCommand() {
        Command incorrectCommand = new Command("incorrect", Collections.emptyList(), out);
        assertEquals(false, printCommand.handleCommand(incorrectCommand));
    }
}
