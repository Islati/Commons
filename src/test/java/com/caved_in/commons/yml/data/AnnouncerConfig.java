package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.Comment;
import com.caved_in.commons.yml.base.Config;

import java.util.ArrayList;
import java.util.HashMap;

public class AnnouncerConfig extends Config {
    @Comment("Order, either sequential or random")
    public String order = "sequential";
    @Comment("The server that should be asked for permissions (Most likely the hub server)")
    public String permissionServer = "lobby";
    @Comment("How often the permissions cache is cleared in minutes. (0=never)")
    public int permissionCacheTime = 0;

    @Comment("A list of announcements (See spigot page for usage)")
    public HashMap<String, MessageMap> servers = new HashMap<String, MessageMap>();


    public static class MessageMap extends Config {
        public ArrayList<String> servers;
        public int offset;
        public int delay;
        public String permission;
        public ArrayList<Announcement> announcements = new ArrayList<Announcement>();
    }

    public static class Announcement extends Config {
        public String type;
        public String message;

        public Announcement clone() {
            Announcement clone = new Announcement();
            clone.type = type + "";
            clone.message = message + "";
            return clone;
        }
    }

    public HashMap<String, BroadcastMap> nonannouncements = new HashMap<String, BroadcastMap>();

    public static class BroadcastMap extends Config {
        public ArrayList<String> servers;
        public String permission;
        public Announcement announcement;
    }

}
