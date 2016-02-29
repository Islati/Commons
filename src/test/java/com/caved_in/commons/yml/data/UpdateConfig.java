package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.base.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class UpdateConfig extends BaseTest {

    @Override
    public void setup() {

    }

    @Override
    @Before
    public void before() throws Exception {
        filename = "updateConfig.yml";

        file = new File(filename);
        if (file.exists()) {
            file.delete();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("IsEnabled: true\n");
        } catch (Exception e) {
            throw e;
        }

        config = new UpdateConfigConfig();
    }

    @Test
    public void init() throws Exception {
        config.init(file);
    }

    @Test
    public void save() throws Exception {
        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "IsEnabled: true\n" +
                "Enabled: false\n" +
                "restriction:\n" +
                "  bedwars.restriction.player: 1\n" +
                "  bedwars.restriction.premium: 3\n");
    }

    @Test
    public void load() throws Exception {
        UpdateConfigConfig config1 = new UpdateConfigConfig();
        config1.load(file);

        Assert.assertEquals(config1.restriction, new HashMap<String, Integer>() {{
            put("bedwars.restriction.player", 1);
            put("bedwars.restriction.premium", 3);
        }});
    }
}
