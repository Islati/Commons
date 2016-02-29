package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ServermenuConfig;
import org.junit.Assert;
import org.junit.Test;

public class ServermenuTest extends BaseTest {
    public void setup() throws Exception {
        config = new ServermenuConfig();
        filename = "serverMenuConfig.yml";
    }

    @Test
    public void onInit() throws Exception {
        config.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents, "menus:\n" +
                "  Game Servers:\n" +
                "    servers:\n" +
                "    - displayPlayers: true\n" +
                "      server: TDM1\n" +
                "      hostName: guerra.year4000.net\n" +
                "      port: 26602\n" +
                "      timeout: null\n" +
                "      displayMotd: false\n" +
                "    title: Game Servers\n");
    }
}
