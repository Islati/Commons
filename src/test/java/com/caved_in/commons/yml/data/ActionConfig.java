package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

public class ActionConfig extends Config {
    public ActionConfig() {
    }

    public ActionConfig(String action) {
        this.action = action;
    }

    public String action = "default action";
}