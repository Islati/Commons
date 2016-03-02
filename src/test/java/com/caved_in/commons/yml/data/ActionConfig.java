package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

public class ActionConfig extends Config {
    public String action = "default action";

    public ActionConfig() {
    }

    public ActionConfig(String action) {
        this.action = action;
        System.out.println("Modified action in ActionConfig for Test SecondExtensionConfigTest");
    }

}