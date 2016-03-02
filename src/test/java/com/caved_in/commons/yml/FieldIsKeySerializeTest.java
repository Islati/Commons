package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.FieldIsKeyConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class FieldIsKeySerializeTest {
    private static FieldIsKeyConfig fieldIsKeyConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        fieldIsKeyConfig = new FieldIsKeyConfig();

        file = new File("temp", "fieldIsKeyConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void initNull() throws InvalidConfigurationException, IOException {
        fieldIsKeyConfig.init(file);

        assertTrue(fieldIsKeyConfig.getYamlConfigurationSettings().getConfigMode() == ConfigMode.FIELD_IS_KEY);

        assertArrayEquals(Util.readFileSplit(file), new String[]{
                "MOTD: '&dWelcome to the server {player}!'",
                "PLAYER_CONNECT_PROXY: '{player}&e has joined the server!'"
        });
    }
}
