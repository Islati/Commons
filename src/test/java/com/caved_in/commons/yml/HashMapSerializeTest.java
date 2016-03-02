package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.HashMapConfig;
import com.caved_in.commons.yml.data.Position;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HashMapSerializeTest {
    private static HashMapConfig hashMapConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        hashMapConfig = new HashMapConfig();

        file = new File("temp", "hashMapConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        hashMapConfig.init(file);

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"TestHashMap:",
                "  test:",
                "    test:",
                "      test1: tesw",
                "TestMap1:",
                "  test:",
                "    x: 0",
                "    y: 0",
                "    z: 0",
                "  test1:",
                "    x: 0",
                "    y: 0",
                "    z: 0"});
    }

    @Test
    public void test2addToHashMap() throws InvalidConfigurationException, IOException {
        hashMapConfig.TestHashMap.get("test").put("test1", new java.util.HashMap<String, String>() {{
            put("tre", "tew");
        }});

        hashMapConfig.save();
        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"TestHashMap:",
                "  test:",
                "    test:",
                "      test1: tesw",
                "    test1:",
                "      tre: tew",
                "TestMap1:",
                "  test:",
                "    x: 0",
                "    y: 0",
                "    z: 0",
                "  test1:",
                "    x: 0",
                "    y: 0",
                "    z: 0"});
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        HashMapConfig hashMapConfig1 = new HashMapConfig();
        hashMapConfig1.load(file);

        Assert.assertEquals(hashMapConfig1.TestHashMap.get("test").size(), 2);
        Assert.assertTrue(hashMapConfig1.TestMap1 instanceof Map);
        Assert.assertEquals(hashMapConfig1.TestMap1.size(), 2);
        Assert.assertTrue(hashMapConfig1.TestMap1.get("test") instanceof Position);
    }
}
