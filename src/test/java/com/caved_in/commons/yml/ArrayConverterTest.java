package com.caved_in.commons.yml;

import com.caved_in.commons.yml.data.ArrayTestConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;


public class ArrayConverterTest {
    static ArrayTestConfig config = null;
    static File file;

    @BeforeClass
    public static void setup() throws Exception {
        config = new ArrayTestConfig();
        file = new File("temp", "arrayConverterTest.yml");
    }

    /**
     * A basic test to confirm, that the used Config can be saved and loaded.
     *
     * @throws InvalidConfigurationException If the configuration cannot be loaded
     */
    @Test
    public void testInitLoad() throws InvalidConfigurationException {
        config.init(file);
        config.load();
    }
}
