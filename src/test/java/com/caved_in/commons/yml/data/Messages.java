package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.Comment;
import com.caved_in.commons.yml.InvalidConfigurationException;
import com.caved_in.commons.yml.base.Config;

import java.io.File;

public class Messages extends Config {
    public static Messages instance = null;

    private Messages() {

        try {
            setConfigHeader(new String[]{"Messages for MapNodes"});
            setConfigFile(new File("temp", "messages.yml"));
            init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get this instance.
     */
    public static String get(String message) {
        if (instance == null)
            instance = new Messages();

        // Check is the message is there if not use the general message.
        String newMessage;
        if (instance.messages.get(message) == null)
            newMessage = instance.messages.get("general");
        else
            newMessage = instance.messages.get(message);

        return newMessage;
    }

    @Comment("All messages that can be changed.")
    private java.util.HashMap<String, String> messages = new java.util.HashMap<String, String>() {{
        put("general", "Did I do that!");

        // Messages for clocks
        put("clock.start", "&6Game starting in &a%s&6.");
        put("clock.restart", "&6Server restarting in &a%s&6.");
    }};
}
