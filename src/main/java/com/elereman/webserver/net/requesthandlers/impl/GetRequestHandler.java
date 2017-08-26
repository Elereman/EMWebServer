package com.elereman.webserver.net.requesthandlers.impl;

import com.elereman.webserver.net.Request;
import com.elereman.webserver.net.requesthandlers.api.RequestHandler;
import com.elereman.webserver.util.FolderView;
import com.elereman.webserver.util.PageNotFound;
import com.elereman.webserver.util.PropertiesHolder;

import java.io.*;

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
    public int handleRequest(Request request) throws IOException {
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
            byte[] response404 = PageNotFound.generateHTML(filepath + fileName).getBytes("UTF8");
            respondHeader("404", "html", response404.length, out);
            out.write(response404);
            return 404;
        }
    }

    private void sendContentOfFile(DataOutputStream out, String file, String mime, String filepath) throws IOException {
        File inputFile = new File(filepath + file);
        InputStream is = new FileInputStream(inputFile);

        try {
            byte[] fileBytes = new byte[2048];

            // Send header
            respondHeader("200", mime, file.length(), out);
            while (is.read(fileBytes) != -1) {
                // Write content of file
                out.write(fileBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            respondHeader("500", mime, 0, out);
        } finally {
            is.close();
        }
    }

    private void sendContentOfFolder(DataOutputStream out, String file, String filepath) throws IOException {
        FolderView folderView = FolderView.getInstance();
        try {
            byte[] fileBytes = folderView.getFolderContext(filepath + file).getBytes("UTF8");
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
