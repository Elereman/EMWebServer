package com.elereman.webserver.net.requesthandlers.api;

import com.elereman.webserver.net.Handler;
import com.elereman.webserver.net.Request;
import com.elereman.webserver.util.PropertiesHolder;

import java.io.DataOutputStream;
import java.util.logging.Logger;

/**
 * Created by Elereman on 28.07.2017.
 */
public interface RequestHandler extends Handler{
    String getType();

    int handleRequest(Request request) throws Exception;
}
