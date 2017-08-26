package com.elereman.webserver.net;

import com.elereman.webserver.net.linkhandlers.api.LinkHandler;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.util.PathUtil;
import com.elereman.webserver.util.PropertiesHolder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerRunnable implements Runnable {
    private PropertiesHolder propertiesHolder;
    private String defaultFile;
    private String filePath;
    private String serverString;
    private Logger log;

    protected Socket socket = null;

    private BufferedReader in;
    private DataOutputStream out;
    private String inString;
    private List<RequestHandler> requestHandlers;
    private List<LinkHandler> linkHandlers;

    public WorkerRunnable(Socket connectionSocket, List<RequestHandler> requestHandlers, List<LinkHandler> linkHandlers) throws Exception {
        propertiesHolder = PropertiesHolder.getInstance();
        defaultFile = propertiesHolder.getDefaultFile();
        filePath = PathUtil.getFilePath();
        serverString = propertiesHolder.getServerString();
        log = propertiesHolder.getLogger();
        this.socket = connectionSocket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.requestHandlers = requestHandlers;
        this.linkHandlers = linkHandlers;
    }

    public void run() {
        try {
            this.inString = this.in.readLine();
            respondContent(this.inString, this.out);

            this.out.flush();
            this.out.close();
            this.in.close();
            socket.close();

        } catch (Exception e) {
            closeSocket();
            log.log(Level.SEVERE, "Exception: ", e);
            log.info("Error flushing and closing");
        }
    }

    private void respondContent(String requestString, DataOutputStream out) throws Exception {
        Request request = getRequestFromClientString(requestString, out);
        String file = request.getFile();

        if (!isPathCorrect(file)) {
            printLogString("(Incorrect symbol) ", 404);
            respondHeader("404", "html", 0, request.getOutputStream());
            closeSocket();
        } else {
            if (tryFindLinkHandler(request) || tryFindRequestHandler(request)) return;

            respondHeader("405", "html", 0, request.getOutputStream());
            printLogString("", 405);
            closeSocket();
        }
    }

    private boolean tryFindLinkHandler(Request request) {
        for (LinkHandler linkHandler : linkHandlers) {
            if (linkHandler.getHandlingLink().equalsIgnoreCase("/" + request.getFile())) {
                int code = linkHandler.handleLink(request);
                printLogString("", code);
                closeSocket();
                return true;
            }
        }
        return false;
    }

    private boolean tryFindRequestHandler(Request request) throws Exception {
        for (RequestHandler requestHandler : requestHandlers) {
            if (requestHandler.getType().equals(request.getMethod())) {
                int code = requestHandler.handleRequest(request);
                if (code != 404) {
                    printLogString("", code);
                    closeSocket();
                    return true;
                } else {
                    StringBuilder message = new StringBuilder();
                    message.append(filePath);
                    message.append(request.getFile());
                    message.append(' ');
                    printLogString(message.toString(), code);
                    closeSocket();
                    return true;
                }
            }
        }
        return false;
    }

    private void printLogString(String message, int code) {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = "[" + dateFormat.format(cal.getTime()) + "] ";
        StringBuilder string = new StringBuilder(time);
        string.append(this.socket.getInetAddress().toString());
        string.append(' ');
        string.append(this.inString);
        string.append(' ');
        string.append(message);
        string.append('(');
        string.append(code);
        string.append(')');
        log.info(string.toString());
    }

    private boolean isPathCorrect(String file) {
        // False if trying to load file outside of web server root or if file contains potentially bad string
        return !(file == null || file.startsWith("//") || file.contains(";") || file.contains("*"));
    }

    private Request getRequestFromClientString(String request, DataOutputStream out) {
        String method = request.substring(0, request.indexOf('/') - 1);
        String file = request.substring(request.indexOf('/') + 1, request.lastIndexOf('/') - 5);
        if (file.equals("") || file.equals("/")) {
            file = defaultFile;
        }
        return new Request(method, file, "HTTP/1.1", out);
    }

    private void respondHeader(String code, String mime, int length, DataOutputStream out) throws Exception {
        out.writeBytes("HTTP/1.0 " + code + " OK\r\n");
        out.writeBytes("Content-Type: " + propertiesHolder.getMimeMap().get(mime) + "\r\n");
        out.writeBytes("Content-Length: " + length + "\r\n");
        out.writeBytes(serverString);
        out.writeBytes("\r\n\r\n");
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Cant close socket", e);
        }
    }
}