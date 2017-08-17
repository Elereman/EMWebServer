package com.elereman.webserver.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elereman on 25.07.2017.
 */
public class PageNotFound {
    public static String generateHTML(String path) {
        StringWriter res = new StringWriter();

        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FolderView.class, "/");
            Template template = cfg.getTemplate("404.ftl");
            Map<String, Object> root = new HashMap<>();
            root.put("folder", path.substring(PathUtil.getFilePath().length() - 1));
            template.process(root, res);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return res.toString();
    }
}
