package com.elereman.webserver;

import com.elereman.webserver.net.WorkerRunnable;
import com.elereman.webserver.net.linkhandlers.api.LinkHandler;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Elereman on 14.08.2017.
 */
public class TestWorkerRunnable {
    private static WorkerRunnable workerRunnable;
    private static OutputStream out;


    @BeforeClass
    public static void beforeClass() throws Exception {
        String inputString = "GET /index.htm HTTP/1.1";

        out = mock(OutputStream.class);
        InputStream in = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        Socket socket = mock(Socket.class);
        LinkHandler linkHandler = mock(LinkHandler.class);
        RequestHandler requestHandler = mock(RequestHandler.class);
        InetAddress inetAddress = mock(InetAddress.class);

        List<LinkHandler> linkHandlers = new ArrayList<>();
        List<RequestHandler> requestHandlers = new ArrayList<>();

        linkHandlers.add(linkHandler);
        requestHandlers.add(requestHandler);

        when(inetAddress.toString()).thenReturn("address");
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        when(socket.getInetAddress()).thenReturn(inetAddress);
        when(linkHandler.getHandlingLink()).thenReturn("GET");
        when(requestHandler.getType()).thenReturn("GET");

        workerRunnable = new WorkerRunnable(socket, requestHandlers, linkHandlers);
    }

    @Test
    public void testRun() {
        workerRunnable.run();
    }
}
