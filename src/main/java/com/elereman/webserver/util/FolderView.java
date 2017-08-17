package com.elereman.webserver.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Elereman on 22.07.2017.
 */
public class FolderView {
    private static volatile FolderView instance;

    //Files to which not be added download tag
    private static final List<String> listTextFiles = new ArrayList<>();

    private FolderView() {
        listTextFiles.add("txt");
        listTextFiles.add("java");
    }

    public static FolderView getInstance() {
        if (instance == null) {
            synchronized (FolderView.class) {
                if (instance == null) {
                    instance = new FolderView();
                    return instance;
                }
            }
        }
        return instance;
    }

    public String getFolderContext(String path) {
        if (!isPathCorrect(path)) {
            throw new RuntimeException("Path can,t be empty!");
        }
        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FolderView.class, "/");
            Template template = cfg.getTemplate("folder.ftl");
            StringWriter res = new StringWriter();
            List<File> files = getFilesFrom(path);
            List<String> links = new LinkedList<>();
            links.add(generateLinkToUp(path, PathUtil.getFilePath()));
            for (File file : files) {
                if (file.isDirectory()) {
                    links.add(generateLinkToFolder(file));
                } else {
                    links.add(generateLinkToFile(file));
                }
            }

            Map<String, Object> root = new HashMap<>();
            root.put("links", links);
            root.put("header", path.substring(PathUtil.getFilePath().length() - 1));
            template.process(root, res);
            return res.toString();
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPathCorrect(String path) {
        return !path.isEmpty();
    }

    private List<File> getFilesFrom(String path) {
        File dir = new File(path);
        List<File> files = new ArrayList<>();
        for (File file : dir.listFiles()) {
            files.add(file);
        }
        return files;
    }

    private String generateLinkToFile(File file) {
        StringBuilder res = new StringBuilder("<a href=\"");
        res.append(file.getParentFile().getName() + '/' + file.getName());
        res.append("\"");
        if (!isTextFile(file)) {
            res.append("download");
        }
        res.append(" title=\"");
        res.append(file.getName());
        res.append("\">");
        res.append(file.getName());
        res.append("</a>");
        return res.toString();
    }

    private String generateLinkToFolder(File folder) {
        StringBuilder res = new StringBuilder("<a href=\"");
        String folderName = folder.getName() + '/';
        res.append(folder.getParentFile().getName() + '/' + folder.getName());
        res.append("\"");
        res.append(" title=\"");
        res.append(folderName);
        res.append("\">");
        res.append(folderName);
        res.append("</a>");
        return res.toString();
    }

    private String generateLinkToUp(String folder, String classPath) {
        StringBuilder lastFolder = new StringBuilder(classPath);
        lastFolder.delete(lastFolder.lastIndexOf("/"), lastFolder.length());
        lastFolder = new StringBuilder(lastFolder.substring(lastFolder.lastIndexOf("/")));
        lastFolder.delete(0, 1);
        StringBuilder prevDirectory = new StringBuilder(folder);
        prevDirectory.delete(prevDirectory.indexOf(classPath), classPath.length() - 1);
        prevDirectory.delete(prevDirectory.lastIndexOf("/"), prevDirectory.length());
        StringBuilder res = new StringBuilder("\n<a href=\"");
        res.append(prevDirectory.toString());
        res.append("\"");
        res.append(" title=\"");
        res.append("../");
        res.append("\">");
        res.append("../");
        res.append("</a>");
        return res.toString();
    }

    private boolean isTextFile(File file) {
        String extension = getFileExtension(file);
        return listTextFiles.contains(extension);
    }

    private String getFileExtension(File file) {
        String res;
        res = file.getName().substring(file.getName().lastIndexOf('.'));
        return res.substring(1);
    }
}
