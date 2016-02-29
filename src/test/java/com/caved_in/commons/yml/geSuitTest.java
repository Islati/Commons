package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.AnnouncementEntry;
import com.caved_in.commons.yml.data.Announcements;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class geSuitTest {
    static Announcements config;
    static File file;

    @BeforeClass
    public void setup() throws Exception {
        config = new Announcements();
        file = new File("temp", "geSuitAnnouncements.yml");
    }

    @Test
    public void test1onInit() throws Exception {
        config.init(file);

        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{
                "Enabled: true",
                "Announcements: {}"
        });
    }

    @Test
    public void test2addAnnouncement() throws Exception {
        if (!((Announcements) config).Announcements.containsKey("global")) {
            AnnouncementEntry announcementEntry = new AnnouncementEntry();
            announcementEntry.Interval = 300;
            announcementEntry.Messages.add("&4Welcome to the server!");
            announcementEntry.Messages.add("&aDon't forget to check out our website");

            ((Announcements) config).Announcements.put("global", announcementEntry);
        }

        if (!((Announcements) config).Announcements.containsKey("test")) {
            AnnouncementEntry announcementEntry = new AnnouncementEntry();
            announcementEntry.Interval = 300;
            announcementEntry.Messages.add("&4Welcome to the server!");
            announcementEntry.Messages.add("&aDon't forget to check out our website");

            ((Announcements) config).Announcements.put("test", announcementEntry);
        }

        if (!((Announcements) config).Announcements.containsKey("test1")) {
            AnnouncementEntry announcementEntry = new AnnouncementEntry();
            announcementEntry.Interval = 300;
            announcementEntry.Messages.add("&4Welcome to the server!");
            announcementEntry.Messages.add("&aDon't forget to check out our website");

            ((Announcements) config).Announcements.put("test1", announcementEntry);
        }

        config.save();
    }

    @Test
    public void test3checkYMLContents() throws Exception {
        Assert.assertArrayEquals(Util.readFileSplit(file), new String[]{"Enabled: true",
                "Announcements:",
                "  test:",
                "    Messages:",
                "    - '&4Welcome to the server!'",
                "    - '&aDon''t forget to check out our website'",
                "    Interval: 300",
                "  global:",
                "    Messages:",
                "    - '&4Welcome to the server!'",
                "    - '&aDon''t forget to check out our website'",
                "    Interval: 300",
                "  test1:",
                "    Messages:",
                "    - '&4Welcome to the server!'",
                "    - '&aDon''t forget to check out our website'",
                "    Interval: 300"});
    }

    @Test
    public void test4checkConfig() throws Exception {
        Announcements announcements = new Announcements();
        announcements.init(file);
        announcements.save();

        Assert.assertTrue(announcements.Announcements.get("global") != null);
        Assert.assertTrue(announcements.Announcements.get("global") instanceof AnnouncementEntry);
        Assert.assertTrue(announcements.Announcements.get("global").Messages instanceof List);
        Assert.assertEquals(announcements.Announcements.get("global").Messages.size(), 2);
        Assert.assertEquals(announcements.Announcements.get("global").Interval, (Integer) 300);
    }
}
