package com.elereman.webserver;

import com.elereman.webserver.net.WebServer;
import com.elereman.webserver.net.requesthandlers.impl.GetRequestHandler;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elereman on 14.08.2017.
 */
public class TestWebServer {
    @Test(expected = IllegalThreadStateException.class)
    public void testStartServer() {
        assertEquals(false, WebServer.startServer());
    }

    @Test
    public void testStopServer() {
        WebServer.stopServer();
    }

    @Test(expected = IllegalThreadStateException.class)
    public void testMainServer() throws Exception {
        WebServer.main(new String[0]);
    }

    @Test
    public void testAddRequestHandler() throws Exception {
        WebServer.addRequestHandler(new GetRequestHandler());
    }
}
