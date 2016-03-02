package com.caved_in.commons.yml;


import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.SetConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SetYamlSerializeTest {
    private static SetConfig setConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        setConfig = new SetConfig();

        file = new File("temp", "setConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        setConfig.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "StringSet:\n" +
                "- Test\n");
    }

    @Test
    public void test2addToSet() throws InvalidConfigurationException, IOException {
        setConfig.StringSet.add("Test1");
        setConfig.save();

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "StringSet:\n" +
                "- Test1\n" +
                "- Test\n");
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        SetConfig setConfig1 = new SetConfig();
        setConfig1.load(file);

        Assert.assertEquals(setConfig1.StringSet.size(), 2);
    }
}
