package com.caved_in.commons.yml;

import java.util.HashMap;
import java.util.Map;

public class Yamled {
    private static Map<Class<? extends YamlConfigurable>, YamledConfiguration> classConfigurationMap = new HashMap<>();

    public static <T extends YamlConfigurable> void init(T configurable) throws InvalidConfigurationException {
        /*
        If we've already initialized the configuration, then don't do it again!
         */
        if (getYamlConfigurationSettings(configurable) != null) {
            return;
        }

        Class<? extends YamlConfigurable> configClass = configurable.getClass();

        /*
        Create the container that holds YamlConfiguration Data
         */
        YamledConfiguration<T> classConfig = new YamledConfiguration<>();

        /*
        Initialize the object that's being serialized, and its options for serialization.
         */
        classConfig.init(configurable);

        classConfigurationMap.put(configClass, classConfig);
    }

    public static <T extends YamlConfigurable> YamledConfiguration<T> getYamlConfigurationSettings(Class<? extends YamlConfigurable> clazz) {
        if (!classConfigurationMap.containsKey(clazz)) {
            return null;
        }

        YamledConfiguration<T> configurationSettings = (YamledConfiguration<T>) classConfigurationMap.get(clazz);

        if (!configurationSettings.getConfiguringClass().equals(clazz)) {
            throw new ClassCastException(String.format("Unable to cast %s to the configurable class of %s", clazz.getCanonicalName(), configurationSettings.getConfiguringClass().getCanonicalName()));
        }

        return configurationSettings;
    }

    public static <T extends YamlConfigurable> YamledConfiguration<T> getYamlConfigurationSettings(T instance) {
        return getYamlConfigurationSettings(instance.getClass());
    }
}
