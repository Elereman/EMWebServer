package com.elereman.webserver.net.linkhandlers.impl;

import com.elereman.webserver.net.linkhandlers.api.LinkHandler;
import com.elereman.webserver.net.Request;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Elereman on 28.07.2017.
 */
public class HelloLinkHandler implements LinkHandler{
    @Override
    public String getHandlingLink() {
        return "/hello";
    }

    @Override
    public int handleLink(Request request) {
        try (OutputStream out = request.getOutputStream()){
            byte[] response = "Hello world!".getBytes();
            respondHeader("200", "html", response.length, request.getOutputStream());
            out.write(response);
            return 200;
        } catch (IOException e) {
            return 500;
        } catch (Exception e) {
            return 500;
        }
    }
}
