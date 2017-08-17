package com.elereman.webserver.util;

import com.elereman.webserver.util.PathUtil;
import com.elereman.webserver.util.PropertiesHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Elereman on 14.08.2017.
 */
public class TestPathUtil {
    private static String filePath;

    @BeforeClass
    public static void beforeClass() {
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

    @Test
    public void test() {
        assertEquals(filePath, PathUtil.getFilePath());
    }
}
