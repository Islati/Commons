package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.PrivateConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrivateVariableSerializeTest {
    private PrivateConfig privateConfig;
    private File file;

    @Before
    public void before() {
        privateConfig = new PrivateConfig();

        file = new File("temp", "privateConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        privateConfig.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "TestBoolean: false\n" +
                "TestInt: 0\n" +
                "TestShort: 0\n" +
                "TestByte: 0\n" +
                "TestDouble: 1.0E-7\n" +
                "TestFloat: 1.0E-4\n" +
                "TestLong: 1684654679684\n" +
                "TestChar: c\n");
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        privateConfig.setTestBoolean(true);
        privateConfig.save();

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "TestBoolean: true\n" +
                "TestInt: 0\n" +
                "TestShort: 0\n" +
                "TestByte: 0\n" +
                "TestDouble: 1.0E-7\n" +
                "TestFloat: 1.0E-4\n" +
                "TestLong: 1684654679684\n" +
                "TestChar: c\n");
    }

    @Test
    public void test3loadConfig() throws Exception {
        PrivateConfig privateConfig1 = new PrivateConfig();
        privateConfig1.load(file);

        Assert.assertEquals(privateConfig1.isTestBoolean(), true);
    }
}
