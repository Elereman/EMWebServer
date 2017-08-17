package com.elereman.webserver.requesthandlers;

import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.net.requesthandlers.impl.GetRequestHandler;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elereman on 12.08.2017.
 */
public class TestHeadRequestHandler {
    static RequestHandler requestHandler = new GetRequestHandler();

    @Test
    public void testGetType() {
        assertEquals("GET", requestHandler.getType());
    }

    @Test
    public void testHandleRequest() throws Exception {
        Request request = new Request("GET", "null", "http/1.1", new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
        assertEquals(404, requestHandler.handleRequest(request));
    }
}
