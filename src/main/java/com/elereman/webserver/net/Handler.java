package com.elereman.webserver.net;

import com.elereman.webserver.util.PropertiesHolder;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Elereman on 05.08.2017.
 */
public interface Handler {
    default void respondHeader(String code, String mime, long length, DataOutputStream out) throws IOException {
        PropertiesHolder propertiesHolder = PropertiesHolder.getInstance();
        if(!propertiesHolder.getMimeMap().containsKey(mime)) {
            mime = "application/" + mime;
        }

        out.writeBytes("HTTP/1.0 " + code + " OK\r\n");
        out.writeBytes("Content-Type: " + propertiesHolder.getMimeMap().get(mime) + "\r\n");
        out.writeBytes("Content-Length: " + length + "\r\n");
        out.writeBytes(propertiesHolder.getServerString());
        out.writeBytes("\r\n\r\n");
    }
}
