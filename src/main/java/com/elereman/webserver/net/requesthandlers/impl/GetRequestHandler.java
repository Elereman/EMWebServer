package com.elereman.webserver.net.requesthandlers.impl;

import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.util.FolderView;
import com.elereman.webserver.util.PageNotFound;
import com.elereman.webserver.util.PropertiesHolder;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Elereman on 21.07.2017.
 */
public class GetRequestHandler implements RequestHandler {
    private String type = "GET";
    private PropertiesHolder propertiesHolder;

    public GetRequestHandler() {
        propertiesHolder = PropertiesHolder.getInstance();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int handleRequest(Request request) throws Exception {
        DataOutputStream out = request.getOutputStream();
        String fileName = request.getFile();
        String mime = fileName.substring(fileName.indexOf('.') + 1);

        String filepath = propertiesHolder.getFilepath();
        try {
            //if target is directory
            File file = new File(filepath + fileName);
            if (file.isDirectory()) {
                sendContentOfFolder(out, fileName, filepath);
                return 200;
            } else {
                sendContentOfFile(out, fileName, mime, filepath);
                return 200;
            }
        } catch (FileNotFoundException e) {
            byte[] response404 = PageNotFound.generateHTML(filepath + fileName).getBytes();
            respondHeader("404", "html", response404.length, out);
            out.write(response404);
            return 404;
        }
    }

    private void sendContentOfFile(DataOutputStream out, String file, String mime, String filepath) throws Exception {
        InputStream is = new FileInputStream(filepath + file);

        try {
            // Open file
            byte[] fileBytes = new byte[is.available()];
            long bytesCount = is.read(fileBytes);

            // Send header
            respondHeader("200", mime, bytesCount, out);

            // Write content of file
            out.write(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    private void sendContentOfFolder(DataOutputStream out, String file, String filepath) throws Exception {
        FolderView folderView = FolderView.getInstance();
        try {
            byte[] fileBytes = folderView.getFolderContext(filepath + file).getBytes();
            // Send header
            respondHeader("200", "html", fileBytes.length, out);

            // Write content of folder
            out.write(fileBytes);
        } catch (RuntimeException e) {
            e.printStackTrace();
            respondHeader("500", "html", 0, out);
        }
    }
}
