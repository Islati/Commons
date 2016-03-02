package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.MultiValueMapConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultiValueMapSerializeTest {
    private static MultiValueMapConfig multiValueMapConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        multiValueMapConfig = new MultiValueMapConfig();

        file = new File("temp", "multiValueMapConfig.yml");
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
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        multiValueMapConfig.init(file);

        String[] fileContents = Util.readFileSplit(file);

        Assert.assertArrayEquals(fileContents, new String[]{"items:",
                "  example:",
                "  - BOOK",
                "  - WRITTEN_BOOK",
                "servers:",
                "  default:",
                "    item:",
                "      name: '&6Server Name'",
                "      type: ENCHANTED_BOOK",
                "    servers:",
                "    - server_one",
                "    - server_two",
                "intMap:",
                "  1: []"});
    }

    @Test
    public void test2addEntryToMap() throws InvalidConfigurationException, IOException {
        multiValueMapConfig.getItems().put("test", new ArrayList<String>());
        multiValueMapConfig.getItems().get("example").add("Test");
        multiValueMapConfig.save();

        String[] fileContents = Util.readFileSplit(file);

        Assert.assertArrayEquals(fileContents, new String[]{"items:",
                "  test: []",
                "  example:",
                "  - BOOK",
                "  - WRITTEN_BOOK",
                "  - Test",
                "servers:",
                "  default:",
                "    item:",
                "      name: '&6Server Name'",
                "      type: ENCHANTED_BOOK",
                "    servers:",
                "    - server_one",
                "    - server_two",
                "intMap:",
                "  1: []"});
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        MultiValueMapConfig multiValueMapConfig1 = new MultiValueMapConfig();
        multiValueMapConfig1.load(file);

        Assert.assertEquals(multiValueMapConfig1.getItems().size(), 2);
        Assert.assertTrue(multiValueMapConfig1.getItems().containsKey("example"));
        Assert.assertEquals(multiValueMapConfig1.getItems().get("example").size(), 3);
        Assert.assertTrue(multiValueMapConfig1.getIntMap().keySet().contains(1));
    }
}
