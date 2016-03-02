package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ServermenuConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class ServermenuTest {
    private static ServermenuConfig config;
    private static File file = null;

    @BeforeClass
    public static void setup() throws Exception {
        config = new ServermenuConfig();
        file = new File("temp", "serverMenuConfig.yml");
    }

    @Test
    public void onInit() throws Exception {
        config.init(file);

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"menus:",
                "  Game Servers:",
                "    servers:",
                "    - displayPlayers: true",
                "      server: TDM1",
                "      hostName: guerra.year4000.net",
                "      port: 26602",
                "      timeout: null",
                "      displayMotd: false",
                "    title: Game Servers"});
    }
}
