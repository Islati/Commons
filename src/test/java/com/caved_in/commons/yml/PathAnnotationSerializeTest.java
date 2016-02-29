package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.PathConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathAnnotationSerializeTest {
    private PathConfig pathConfig;
    private File file;

    @Before
    public void before() {
        pathConfig = new PathConfig();

        file = new File("temp", "pathConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        pathConfig.init(file);

        String fileContents = Util.readFile(file);
        System.out.println(fileContents);

        Assert.assertEquals(fileContents.replace("\r", ""), "# test\n" +
                "config-with-dash: true\n");
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        pathConfig.Test = false;
        pathConfig.save();

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "# test\n" +
                "config-with-dash: false\n");
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        PathConfig pathConfig1 = new PathConfig();
        pathConfig1.load(file);

        Assert.assertTrue(!pathConfig1.Test);
    }
}
