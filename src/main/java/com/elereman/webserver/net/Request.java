package com.elereman.webserver.net;

import java.io.DataOutputStream;

/**
 * Created by Elereman on 25.07.2017.
 */
public class Request {
    private String method;
    private String file;
    private String protocol;
    private DataOutputStream out;

    public Request(String method, String file, String protocol, DataOutputStream outputStream) {
        this.method = method;
        this.file = file;
        this.protocol = protocol;
        this.out = outputStream;
    }

    public String getMethod() {
        return method;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol() {
        return protocol;
    }

    public DataOutputStream getOutputStream() {
        return out;
    }
}
