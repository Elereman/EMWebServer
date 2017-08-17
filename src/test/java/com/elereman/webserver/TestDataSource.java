package com.elereman.webserver;

import com.elereman.webserver.io.api.DataSource;
import com.elereman.webserver.io.impl.FileDataSource;
import com.elereman.webserver.io.impl.ResourcesDataSource;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Elereman on 08.08.2017.
 */
public class TestDataSource {

    @Test
    public void testGetPropertyFileDataSource() {
        DataSource dataSource = new FileDataSource("server.properties");
        assertEquals("8080", dataSource.getProperty("port"));
        assertEquals("Server: EMWEBServer/0.2", dataSource.getProperty("server-string"));
        assertEquals("index.html", dataSource.getProperty("default-file"));
    }

    @Test
    public void testGetPropertyResourcesDataSource() {
        DataSource dataSource = new ResourcesDataSource("server.properties");
        assertEquals("8080", dataSource.getProperty("port"));
        assertEquals("Server: EMWEBServer/0.2", dataSource.getProperty("server-string"));
        assertEquals("index.html", dataSource.getProperty("default-file"));
    }
}
