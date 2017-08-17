package com.elereman.webserver.util;

/**
 * Created by Elereman on 28.07.2017.
 */
public class PathUtil {
    private static String filePath;

    public static String getFilePath() {
        if (filePath != null) {
            synchronized (PathUtil.class) {
                if (filePath != null) {
                    return filePath;
                }
            }
        } else {
            StringBuilder path;
            path = new StringBuilder(PropertiesHolder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            path.delete(path.lastIndexOf("/"), path.length());
            path.append('/');

            if (System.getProperty("os.name").startsWith("Windows")) {
                filePath = path.toString().substring(1);
            } else {
                filePath = path.toString();
            }
        }
        return filePath;
    }
}
