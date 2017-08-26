package com.elereman.webserver.io.impl;

import com.elereman.webserver.io.api.DataSource;
import com.elereman.webserver.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Elereman on 13.07.2017.
 */
public class FileDataSource implements DataSource {
    private File file;
    private Map<String, String> properties;

    public FileDataSource(String name) {
        this(PathUtil.getFilePath(), name);
    }

    public FileDataSource(String path, String name) {
        if (!path.equals("") && !name.equals("")) {
            file = new File(path + name);
            if (!file.exists()) {
                throw new RuntimeException("File " + file.getAbsolutePath() + " is not exists!");
            }
        } else {
            throw new RuntimeException("Arguments can't be empty!");
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
        try (Scanner scanner = new Scanner(file, "UTF8")) {
            while (scanner.hasNextLine()) {
                String fileString = scanner.nextLine();
                properties.put(parseName(fileString), parseVolume(fileString));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String parseVolume(String target) {
        return target.substring(target.lastIndexOf('=') + 1);
    }

    private String parseName(String target) {
        return target.substring(0, target.lastIndexOf('='));
    }
}
