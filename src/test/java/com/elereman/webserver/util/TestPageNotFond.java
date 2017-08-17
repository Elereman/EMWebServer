package com.elereman.webserver.util;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elereman on 17.08.2017.
 */
public class TestPageNotFond {
    @Test
    public void testGetFolderContext() {
        String folderContent = PageNotFound.generateHTML(PathUtil.getFilePath());
        String str = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
        assertEquals(true, folderContent.contains(str));
    }
}
