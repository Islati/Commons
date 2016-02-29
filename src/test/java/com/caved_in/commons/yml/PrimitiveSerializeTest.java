package com.caved_in.commons.yml;

import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.PrimitiveConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrimitiveSerializeTest {
    private static PrimitiveConfig primitiveConfig = new PrimitiveConfig();
    private static File file = null;

    @BeforeClass
    public static void test0Setup() {
        file = new File("temp", "primitiveConfig.yml");

        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileUtils.forceMkdir(file.getParentFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        primitiveConfig = new PrimitiveConfig();
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        primitiveConfig.init(file);

        String fileContents = Util.readFile(file);

        String[] fileLines = StringUtil.splitOnNewline(fileContents);

        assertArrayEquals(fileLines, new String[]{
                "TestBoolean: false",
                "TestInt: 0",
                "TestShort: 0",
                "TestByte: 0",
                "TestDouble: 1.0E-7",
                "TestFloat: 1.0E-4",
                "TestLong: 1684654679684",
                "TestChar: c",
        });
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        primitiveConfig.TestBoolean = true;

        primitiveConfig.save();

        String fileContents = Util.readFile(file);
        assertTrue(fileContents.contains("TestBoolean: true"));
    }

    @Test
    public void test3loadConfig() throws Exception {
        PrimitiveConfig primitiveConfig1 = new PrimitiveConfig();

        primitiveConfig1.load(file);

        Assert.assertEquals(primitiveConfig1.TestBoolean, true);

        primitiveConfig1.save();

        String fileContents = Util.readFile(file);
        assertTrue(fileContents.contains("TestBoolean: true"));
        assertTrue(fileContents.contains("TestInjectUpdate: 'true'"));
        assertTrue(fileContents.contains("TestInt"));
    }
}
