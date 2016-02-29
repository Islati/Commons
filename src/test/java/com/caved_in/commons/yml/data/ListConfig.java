package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

import java.util.ArrayList;

public class ListConfig extends Config {
    public ArrayList<ListSubConfig> TestList = new ArrayList<ListSubConfig>() {{
        add(new ListSubConfig());
    }};
}
