package com.elereman.webserver.util;

import com.elereman.webserver.util.FolderView;
import com.elereman.webserver.util.PathUtil;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elereman on 10.08.2017.
 */
public class TestFolderView {
    private static FolderView folderView;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        folderView = FolderView.getInstance();
    }

    @Test
    public void testGetFolderContextException() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Path can,t be empty!");
        folderView.getFolderContext("");
    }

    @Test
    public void testGetFolderContext() {
        String folderContent = folderView.getFolderContext(PathUtil.getFilePath());
        String str = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
        assertEquals(true, folderContent.contains(str));
    }

    @Test
    public void testGetInstance() {
        assertEquals(folderView, FolderView.getInstance());
    }
}
