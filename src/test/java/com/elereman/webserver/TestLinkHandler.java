package com.elereman.webserver;

import com.elereman.webserver.io.api.DataSource;
import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.linkhandlers.impl.HelloLinkHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Elereman on 14.08.2017.
 */
public class TestLinkHandler {
    private static HelloLinkHandler linkHandler;
    private static Request request;
    private static DataOutputStream out;
    private static PrintStream mock;

    @BeforeClass
    public static void beforeClass() throws Exception{
        linkHandler = new HelloLinkHandler();
        mock = mock(PrintStream.class);
        out = new DataOutputStream(mock);
        request = new Request("HEAD", "null", "http/1.1", out);
    }

    @Test
    public void testGetHandlingLink() {
        assertEquals("/hello", linkHandler.getHandlingLink());
    }

    @Test
    public void testRespondHeader() throws Exception {
        StringBuilder s = new StringBuilder();
        DataOutputStream out = new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
               s.append(b);
            }
        });
        String string = "72848480474946483250484832797513106711111011610111011645841211" +
                "121015832116101120116471041161091081310671111101161011101164576101110103116104583248131083101114118101" +
                "11458326977876966831011141181011144748465013101310";
        linkHandler.respondHeader("200", "html", 0, out);
        assertEquals(string, s.toString());
    }

    @Test
    public void testHandleLink() throws Exception{
        linkHandler.handleLink(request);
        verify(mock, times(89)).write(anyByte());
        verify(mock, times(1)).close();
    }

    @Test
    public void testHandleLinkWithNPException() throws Exception{
        Request request = new Request("HEAD", "null", "http/1.1", null);
        linkHandler.handleLink(request);
        assertEquals(500, linkHandler.handleLink(request));
    }

    @Test
    public void testHandleLinkWithIOException() throws Exception{
        DataOutputStream out = new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Test IOException");
            }
        });
        Request request = new Request("HEAD", "null", "http/1.1", out);
        linkHandler.handleLink(request);
        assertEquals(500, linkHandler.handleLink(request));
    }
}
