package com.caved_in.commons.yml;


import java.io.File;

public class YamlConfig implements YamlConfigurable {
    @YamlExclude
    File CONFIG_FILE = null;

    public YamlConfig() {

    }

    public YamlConfig(String filename) {
        setConfigFile(new File(filename + (filename.endsWith(".yml") ? "" : ".yml")));
        try {
            init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfig(File file) {
        setConfigFile(file);
        try {
            init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getConfigFile() {
        return CONFIG_FILE;
    }

    @Override
    public void setConfigFile(File file) {
        CONFIG_FILE = file;
    }

    public void setConfigHeader(String[] header) {
        getYamlConfigurationSettings().setConfigHeader(header);
    }
}
