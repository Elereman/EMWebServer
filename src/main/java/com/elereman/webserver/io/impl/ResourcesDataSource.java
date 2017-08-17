package com.elereman.webserver.io.impl;

import com.elereman.webserver.io.api.DataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Elereman on 03.08.2017.
 */
public class ResourcesDataSource implements DataSource {
    private Scanner scanner;
    private Map<String, String> properties;

    public ResourcesDataSource(String name) {
        if (name.equals("")) {
            throw new RuntimeException("File name can't be empty!");
        } else {
            scanner = new Scanner(ResourcesDataSource.class.getClassLoader().getResourceAsStream(name));
        }
    }

    @Override
    public String getProperty(String name) {
        if (properties == null) {
            readProperties();
        }
        return properties.get(name);
    }

    private void readProperties() {
        properties = new HashMap<>();
        while (scanner.hasNextLine()) {
            String fileString = scanner.nextLine();
            properties.put(parseName(fileString), parseVolume(fileString));
        }
        scanner.close();
    }

    private String parseVolume(String target) {
        return target.substring(target.lastIndexOf('=') + 1);
    }

    private String parseName(String target) {
        return target.substring(0, target.lastIndexOf('='));
    }
}
