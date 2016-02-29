package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

import java.util.HashMap;

public class HashMapConfig extends Config {
    public HashMap<String, HashMap<String, HashMap<String, String>>> TestHashMap = new HashMap<String, HashMap<String, HashMap<String, String>>>() {{
        put("test", new HashMap<String, HashMap<String, String>>() {{
            put("test", new HashMap<String, String>() {{
                put("test1", "tesw");
            }});
        }});
    }};

    public HashMap<String, Position> TestMap1 = new HashMap<String, Position>() {{
        put("test", new Position());
        put("test1", new Position());
    }};
}
