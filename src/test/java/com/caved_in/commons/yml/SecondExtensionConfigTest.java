package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ExtendTestConfig;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecondExtensionConfigTest extends BaseTest {
    private ExtendTestConfig extendedConfig;

    public void setup() {
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

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "action:\n" +
                "  action: default action\n" +
                "trigAct:\n" +
                "  action: default action\n" +
                "  trigger: default trigger\n" +
                "actionCtor:\n" +
                "  action: another action (2)\n" +
                "trigActCtor:\n" +
                "  action: yet another action (3)\n" +
                "  trigger: another trigger (3)\n");
    }

    @Test
    public void test2changeBoolean() throws InvalidConfigurationException, IOException {
        extendedConfig.action.action = "test1";
        extendedConfig.save();

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "action:\n" +
                "  action: test1\n" +
                "trigAct:\n" +
                "  action: default action\n" +
                "  trigger: default trigger\n" +
                "actionCtor:\n" +
                "  action: another action (2)\n" +
                "trigActCtor:\n" +
                "  action: yet another action (3)\n" +
                "  trigger: another trigger (3)\n");
    }

    @Test
    public void test3loadConfig() throws InvalidConfigurationException {
        ExtendTestConfig extendedConfig1 = new ExtendTestConfig();
        extendedConfig1.load(file);

        Assert.assertEquals(extendedConfig1.trigActCtor.trigger, "another trigger (3)");
        Assert.assertEquals(extendedConfig1.trigActCtor.action, "yet another action (3)");
    }
}
