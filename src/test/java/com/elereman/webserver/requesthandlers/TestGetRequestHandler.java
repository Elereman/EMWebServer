package com.elereman.webserver.requesthandlers;

import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.net.requesthandlers.impl.HeadRequestHandler;
import com.elereman.webserver.util.PathUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * Created by Elereman on 12.08.2017.
 */
public class TestGetRequestHandler {
    static RequestHandler requestHandler = new HeadRequestHandler();

    @Test
    public void testGetType() {
        assertEquals("HEAD", requestHandler.getType());
    }

    @Test
    public void testHandleRequest() throws Exception{
        Request request = new Request("HEAD", "null.txt", "http/1.1", new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
        assertEquals(200, requestHandler.handleRequest(request));
    }

    @Test
    public void testHandleRequestIfNotFolder() throws Exception{
        Request request = new Request("HEAD",
                PathUtil.getFilePath() + "/com/elereman/webserver/util/PropertiesHolder.class",
                "http/1.1", new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
        assertEquals(200, requestHandler.handleRequest(request));
    }

    @Test
    public void testHandleRequestIfFolder() throws Exception{
        Request request = new Request("HEAD", PathUtil.getFilePath() + "/com", "http/1.1", new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
        assertEquals(200, requestHandler.handleRequest(request));
    }
}
