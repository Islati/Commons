package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.AnnouncerConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoAnnounceTest {
    private static AnnouncerConfig config;
    private static File file;

    @BeforeClass
    public static void setup() throws Exception {
        config = new AnnouncerConfig();
        file = new File("temp", "noAnnounceAnnouncerConfig.yml");
    }

    @Test
    public void onInit() throws Exception {
        config.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents, "# Order, either sequential or random\n" +
                "order: sequential\n" +
                "# The server that should be asked for permissions (Most likely the hub server)\n" +
                "permissionServer: lobby\n" +
                "# How often the permissions cache is cleared in minutes. (0=never)\n" +
                "permissionCacheTime: 0\n" +
                "# A list of announcements (See spigot page for usage)\n" +
                "servers: {}\n" +
                "nonannouncements: {}\n");
    }
}
