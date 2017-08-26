package com.elereman.webserver.net.requesthandlers.api;

import com.elereman.webserver.net.Handler;
import com.elereman.webserver.net.Request;

import java.io.IOException;

/**
 * Created by Elereman on 28.07.2017.
 */
public interface RequestHandler extends Handler {
    String getType();

    int handleRequest(Request request) throws IOException;
}
