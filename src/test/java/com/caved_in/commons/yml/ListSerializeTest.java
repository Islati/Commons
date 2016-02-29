package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ListConfig;
import com.caved_in.commons.yml.data.ListSubConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListSerializeTest {
    private static ListConfig listConfig;
    private static File file;

    @BeforeClass
    public void before() {
        listConfig = new ListConfig();

        file = new File("temp", "listConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        listConfig.init(file);

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"TestList:",
                "- Test: Test"});
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        listConfig.TestList.add(new ListSubConfig());
        listConfig.save();

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"TestList:",
                "- Test: Test",
                "- Test: Test"});
    }

    @Test
    public void loadConfig() throws InvalidConfigurationException {
        ListConfig listConfig1 = new ListConfig();
        listConfig1.load(file);

        Assert.assertEquals(listConfig1.TestList.size(), 2);
    }
}
