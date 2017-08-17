package com.elereman.webserver;

import com.elereman.webserver.console.Console;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

/**
 * Created by Elereman on 13.08.2017.
 */
public class TestConsole {
    private static Console console;
    private static PrintStream out;

    @BeforeClass
    public static void beforeClass() {
        console = new Console();
        out = mock(PrintStream.class);
    }

    @Test
    public void testDoCommand() {
        console.doCommand("print a", out);
        verify(out).println("a");
    }

    @Test
    public void testDoCommandBadCommand() {
        console.doCommand("prin", out);
        verify(out).println("Command prin is incorrect! Use help");
    }

    @Test
    public void testDoCommandIncorrectCommand() {
        console.doCommand("print", out);
        verify(out).println("Use print [arg]");
    }

    @Test
    public void testDoCommandHelpCommand() {
        console.doCommand("help", out);
        verify(out).println("start");
    }
}
