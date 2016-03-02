package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ExtendTestConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecondExtensionConfigTest {
    private static ExtendTestConfig extendedConfig;
    private static File file;

    @BeforeClass
    public static void setup() {
        extendedConfig = new ExtendTestConfig();

        file = new File("temp", "extendedTestConfig.yml");
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

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{
                "action:",
                "  action: default action",
                "trigAct:",
                "  action: default action",
                "  trigger: default trigger",
                "actionCtor:",
                "  action: another action (2)",
                "trigActCtor:",
                "  action: yet another action (3)",
                "  trigger: another trigger (3)"});
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        extendedConfig.action.action = "test1";
        extendedConfig.save();

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"action:",
                "  action: test1",
                "trigAct:",
                "  action: default action",
                "  trigger: default trigger",
                "actionCtor:",
                "  action: another action (2)",
                "trigActCtor:",
                "  action: yet another action (3)",
                "  trigger: another trigger (3)"});
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        ExtendTestConfig extendedConfig1 = new ExtendTestConfig();
        extendedConfig1.load(file);

        Assert.assertEquals(extendedConfig1.trigActCtor.trigger, "another trigger (3)");
        Assert.assertEquals(extendedConfig1.trigActCtor.action, "yet another action (3)");
    }
}
