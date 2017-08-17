package com.elereman.webserver.util;

import com.elereman.webserver.io.api.DataSource;
import com.elereman.webserver.io.impl.FileDataSource;
import com.elereman.webserver.io.impl.ResourcesDataSource;
import com.elereman.webserver.net.WebServer;

import java.io.IOException;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Elereman on 08.07.2017.
 */
public class PropertiesHolder {
    private final int PORT;
    private final String DEFAULT_FILE;
    // FILEPATH points to root of web server files
    private final String FILEPATH;
    private final String SERVER_STRING;
    private static PropertiesHolder instance;
    private Logger log;


    private final Map<String, String> mimeMap = new HashMap<>();

    private PropertiesHolder(DataSource dataSource) {
        mimeMap.put("html", "text/html");
        mimeMap.put("css", "text/css");
        mimeMap.put("js", "application/js");
        mimeMap.put("jpg", "image/jpg");
        mimeMap.put("jpeg", "image/jpeg");
        mimeMap.put("png", "image/png");

        List<String> propertiesNames = new ArrayList<>();
        propertiesNames.add("port");
        propertiesNames.add("default-file");
        propertiesNames.add("server-string");

        PORT = Integer.parseInt(dataSource.getProperty(propertiesNames.get(0)));
        DEFAULT_FILE = dataSource.getProperty(propertiesNames.get(1));
        SERVER_STRING = dataSource.getProperty(propertiesNames.get(2));
        FILEPATH = PathUtil.getFilePath();
        log = Logger.getLogger(WebServer.class.getName());

        String logProperties = "logging.properties";
        try {
            LogManager.getLogManager().readConfiguration(
                    PropertiesHolder.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException("File: " + logProperties + "not found!", e);
        }
    }

    public static PropertiesHolder getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (PropertiesHolder.class) {
                if (instance == null) {
                    instance = new PropertiesHolder(dataSource);
                    return instance;
                }
            }
        } else {
            return instance;
        }
        return instance;
    }

    public static PropertiesHolder getInstance() {
        if (instance == null) {
            synchronized (PropertiesHolder.class) {
                DataSource dataSource;
                String fileName = "server.properties";
                try {
                    dataSource = new FileDataSource(fileName);
                } catch (RuntimeException e) {
                    Logger log = Logger.getLogger(WebServer.class.getName());
                    log.info("Settings file " + fileName + " not found! Using default settings!");
                    dataSource = new ResourcesDataSource(fileName);
                }
                return getInstance(dataSource);
            }
        } else {
            return instance;
        }
    }

    public int getPort() {
        return PORT;
    }

    public String getDefaultFile() {
        return DEFAULT_FILE;
    }

    public String getFilepath() {
        return FILEPATH;
    }

    public String getServerString() {
        return SERVER_STRING;
    }

    public Map<String, String> getMimeMap() {
        return Collections.unmodifiableMap(mimeMap);
    }

    public Logger getLogger() {
        return log;
    }
}
