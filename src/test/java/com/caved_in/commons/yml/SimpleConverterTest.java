package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ObjectConverter;
import com.caved_in.commons.yml.data.SimpleObjectConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;


public class SimpleConverterTest {
    static SimpleObjectConfig config = null;
    static File file;

    @BeforeClass
    public static void setup() throws Exception {
        config = new SimpleObjectConfig();
        config.getYamlConfigurationSettings().addConverter(ObjectConverter.class);
        file = new File("temp", "simpleConverterTest.yml");
    }

    @Test
    public void onInit() throws Exception {
        config.init(file);

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{
                "TestMap:",
                "  test: test",
                "TestSet:",
                "- test"});
    }
}
