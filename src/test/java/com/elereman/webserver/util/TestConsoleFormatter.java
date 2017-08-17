package com.elereman.webserver.util;

import com.elereman.webserver.util.ConsoleFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.Assert.*;


/**
 * Created by Elereman on 10.08.2017.
 */
public class TestConsoleFormatter {
    private static ConsoleFormatter formatter;

    @BeforeClass
    public static void beforeClass() {
        formatter = new ConsoleFormatter();
    }

    @Test
    public void testFormat() {
        String actual = formatter.format(new LogRecord(Level.INFO, "test"));
        assertEquals("INFO: test\n", actual);
    }
}
