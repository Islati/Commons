package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.AnnouncerConfig;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoAnnounceTest extends BaseTest {

    public void setup() throws Exception {
        config = new AnnouncerConfig();
        filename = "noAnnounceAnnouncerConfig.yml";
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
