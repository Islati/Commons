package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

import java.util.HashSet;

public class SetConfig extends Config {
    public java.util.Set<String> StringSet = new HashSet<String>() {{
        add("Test");
    }};
}
