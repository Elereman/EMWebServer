package com.elereman.webserver.util;

import com.elereman.webserver.io.api.DataSource;
import com.elereman.webserver.util.PathUtil;
import com.elereman.webserver.util.PropertiesHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Elereman on 10.08.2017.
 */
public class TestPropertiesHolder {
    private static PropertiesHolder propertiesHolder;

    @BeforeClass
    public static void beforeClass() throws NoSuchFieldException, IllegalAccessException {
        Field field = PropertiesHolder.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(PropertiesHolder.class, null);
        DataSource mock = mock(DataSource.class);
        when(mock.getProperty(anyString())).thenReturn("1234");
        propertiesHolder = PropertiesHolder.getInstance(mock);
    }

    @Test
    public void testGetDefaultFile() {
        assertEquals("1234", propertiesHolder.getDefaultFile());
    }

    @Test
    public void testGetPort() {
        assertEquals(1234, propertiesHolder.getPort());
    }

    @Test
    public void tesGetFilepath() {
        assertEquals(PathUtil.getFilePath(), propertiesHolder.getFilepath());
    }

    @Test
    public void testGetServerString() {
        assertEquals("1234", propertiesHolder.getServerString());
    }

    @Test
    public void testGetInstance() {
        assertEquals(propertiesHolder, PropertiesHolder.getInstance());
    }
}
