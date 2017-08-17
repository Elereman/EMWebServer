package com.elereman.webserver;

import com.elereman.webserver.net.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataOutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Elereman on 13.08.2017.
 */
public class TestRequest {
    private static Request request;
    private static DataOutputStream out = mock(DataOutputStream.class);

    @BeforeClass
    public static void beforeClass() {
        request = new Request("HEAD", "null", "http/1.1", out);
    }

    @Test
    public void testGetProtocol() {
        assertEquals("http/1.1", request.getProtocol());
    }

    @Test
    public void testGetMethod() {
        assertEquals("HEAD", request.getMethod());
    }

    @Test
    public void testGetOutputStream() {
        assertEquals(out, request.getOutputStream());
    }

    @Test
    public void testGetFile() {
        assertEquals("null", request.getFile());
    }
}
