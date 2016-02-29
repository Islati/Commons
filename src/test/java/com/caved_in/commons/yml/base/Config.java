package com.caved_in.commons.yml.base;

import com.caved_in.commons.yml.YamlConfig;

import java.io.File;

public class Config extends YamlConfig {
    public Config() {

    }

    public Config(File file) {
        super(file);
    }

    public Config(String filename) {
        super(filename);
    }
}
