package com.zarusz.control.domain.expressions.model.serialization;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class TestUtil {

    public static String readTestData(String name, Object classLoader) throws IOException {
        InputStream in = classLoader.getClass().getResourceAsStream(name);
        if (in == null) {
            throw new FileNotFoundException("File does not exist");
        }
        try {
            return IOUtils.toString(in, "UTF-8");
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
