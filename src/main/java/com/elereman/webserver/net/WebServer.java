package com.elereman.webserver.net;


import com.elereman.webserver.console.Console;
import com.elereman.webserver.net.linkhandlers.api.LinkHandler;
import com.elereman.webserver.net.linkhandlers.impl.HelloLinkHandler;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.net.requesthandlers.impl.GetRequestHandler;
import com.elereman.webserver.net.requesthandlers.impl.HeadRequestHandler;
import com.elereman.webserver.util.PropertiesHolder;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class WebServer implements Runnable {
    private static PropertiesHolder propertiesHolder;
    private static int port;
    private static Thread instance = new Thread(new WebServer());
    private static List<RequestHandler> requestHandlers = new ArrayList<>();
    private static List<LinkHandler> linkHandlers = new ArrayList<>();

    public static void main(String argv[]) throws Exception {
        propertiesHolder = PropertiesHolder.getInstance();
        port = propertiesHolder.getPort();
        startServer();
    }

    public static void addRequestHandler(RequestHandler requestHandler) {
        requestHandlers.add(requestHandler);
    }

    public static boolean stopServer() {
        instance.interrupt();
        return true;
    }

    public static boolean startServer() {
        if (!instance.isAlive()) {
            instance.start();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        Console console = new Console();
        Thread cos = new Thread(new ConsoleRunnable(console, System.in));
        cos.start();

        requestHandlers.add(new GetRequestHandler());
        requestHandlers.add(new HeadRequestHandler());

        linkHandlers.add(new HelloLinkHandler());

        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket connectionSocket = serverSocket.accept();
                executor.execute(new WorkerRunnable(connectionSocket, requestHandlers, linkHandlers));
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            executor.shutdown();
            cos.interrupt();
        }
    }
}

class ConsoleRunnable implements Runnable {
    Console console;
    Scanner in;

    public ConsoleRunnable(Console console, InputStream in) {
        this.console = console;
        this.in = new Scanner(in);
    }

    @Override
    public void run() {
        while (true) {
            console.doCommand(in.nextLine(), System.out);
        }
    }
}
