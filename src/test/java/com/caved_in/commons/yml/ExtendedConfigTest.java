package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ExtendedConfigConfig;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExtendedConfigTest {
    private static ExtendedConfigConfig extendedConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        extendedConfig = new ExtendedConfigConfig();

        file = new File("temp", "extendedConfig.yml");
        extendedConfig.setConfigFile(file);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        extendedConfig.init(file);

        String[] fileContents = Util.readFileSplit(file);

        assertArrayEquals(fileContents, new String[]{"test: test",
                "test1: test"});
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        extendedConfig.test = "test1";
        extendedConfig.save();

        assertArrayEquals(Util.readFileSplit(file), new String[]{"test: test1",
                "test1: test"});
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        ExtendedConfigConfig extendedConfig1 = new ExtendedConfigConfig();
        extendedConfig1.load(file);

        assertEquals(extendedConfig1.test1, "test");
        assertEquals(extendedConfig1.test, "test1");
    }
}
