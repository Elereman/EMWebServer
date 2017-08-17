package com.elereman.webserver.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by Elereman on 04.08.2017.
 */
public class ConsoleFormatter extends Formatter{
    @Override
    public String format(LogRecord record) {
        StringBuilder result = new StringBuilder();
        result.append(record.getLevel().toString());
        result.append(": ");
        result.append(record.getMessage());
        result.append('\n');
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            result.append(sw.toString());
        }
        return result.toString();
    }
}
