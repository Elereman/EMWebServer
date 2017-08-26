package com.elereman.webserver.net.requesthandlers.impl;

import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by Elereman on 25.07.2017.
 */
public class HeadRequestHandler implements RequestHandler {
    @Override
    public String getType() {
        return "HEAD";
    }

    @Override
    public int handleRequest(Request request) throws IOException {
        try {
            File file = getFile(request.getFile());
            String mime = getMime(file.getName());
            respondHeader("200", mime, file.length(), request.getOutputStream());
            return 200;
        } catch (RuntimeException e) {
            e.printStackTrace();
            respondHeader("500", "html", 0, request.getOutputStream());
            return 500;
        }
    }

    private String getMime(String file) {
        String mime = file.substring(file.indexOf(".") + 1);
        return mime;
    }

    private File getFile(String path) {
        return new File(path);
    }
}
