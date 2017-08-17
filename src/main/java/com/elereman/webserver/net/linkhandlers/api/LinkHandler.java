package com.elereman.webserver.net.linkhandlers.api;

import com.elereman.webserver.net.Handler;
import com.elereman.webserver.net.Request;

/**
 * Created by Elereman on 28.07.2017.
 */
public interface LinkHandler extends Handler{
    String getHandlingLink();

    int handleLink(Request request);
}
