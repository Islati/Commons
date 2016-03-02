package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.UpdateConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class UpdatedConfigTest {
    private static File file;
    private static UpdateConfig config;

    @BeforeClass
    public static void before() throws Exception {
        file = new File("temp", "updateConfig.yml");
        if (file.exists()) {
            file.delete();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("IsEnabled: true");
        } catch (Exception e) {
            throw e;
        }

        config = new UpdateConfig();
    }

    @Test
    public void init() throws Exception {
        config.init(file);
    }

    @Test
    public void save() throws Exception {
        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"IsEnabled: true",
                "Enabled: false",
                "restriction:",
                "  bedwars.restriction.player: 1",
                "  bedwars.restriction.premium: 3"});
    }

    @Test
    public void load() throws Exception {
        UpdateConfig config1 = new UpdateConfig();
        config1.load(file);

        Assert.assertEquals(config1.restriction, new HashMap<String, Integer>() {{
            put("bedwars.restriction.player", 1);
            put("bedwars.restriction.premium", 3);
        }});
    }
}
