package com.caved_in.commons.yml.data;


import com.caved_in.commons.yml.base.Config;

import java.util.HashMap;

public class Announcements extends Config {
    public Boolean Enabled = true;
    public HashMap<String, AnnouncementEntry> Announcements = new HashMap<>();
}
