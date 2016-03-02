package com.caved_in.commons.yml;

import java.util.HashMap;
import java.util.Map;

public class Yamled {
    private static Map<Class<? extends YamlConfigurable>, YamledConfiguration> classConfigurationMap = new HashMap<>();

    public static <T extends YamlConfigurable> boolean hasConfiguration(Class<? extends YamlConfigurable> clazz) {
        return classConfigurationMap.containsKey(clazz);
    }

    public static <T extends YamlConfigurable> boolean hasConfiguration(T configurable) {
        return hasConfiguration(configurable.getClass());
    }

    public static <T extends YamlConfigurable> void init(T configurable) throws InvalidConfigurationException {
        /*
        If we've already initialized the configuration, then don't do it again!
         As configuration is based off classes, not the object itself, we don't require
         a second instance for any of them!
         */
        if (hasConfiguration(configurable)) {
            return;
        }

        /*
        Create the container that holds YamlConfiguration Data
         */
        YamledConfiguration<T> classConfig = new YamledConfiguration<>();

        /*
        Initialize the object that's being serialized, and its options for serialization.
         */
        classConfig.init(configurable);

        classConfigurationMap.put(configurable.getClass(), classConfig);
    }

    public static <T extends YamlConfigurable> YamledConfiguration<T> getYamlConfigurationSettings(Class<T> clazz) {
        YamledConfiguration yamledConfig = classConfigurationMap.get(clazz);

        if (!yamledConfig.getConfiguringClass().equals(clazz)) {
            throw new IllegalAccessError(String.format("Configurable class %s does not equal instance class %s", clazz.getCanonicalName(), yamledConfig.getConfiguringClass().getCanonicalName()));
        }

        return (YamledConfiguration<T>) yamledConfig;
    }

    public static <T extends YamlConfigurable> YamledConfiguration<T> getYamlConfigurationSettings(T instance) {
        Class<? extends YamlConfigurable> configClass = instance.getClass();
        YamledConfiguration yamledConfig = classConfigurationMap.get(configClass);
        if (!yamledConfig.getConfiguringClass().equals(configClass)) {
            throw new IllegalAccessError(String.format("Configurable class %s does not equal instance class %s", configClass.getCanonicalName(), yamledConfig.getConfiguringClass().getCanonicalName()));
        }

        return (YamledConfiguration<T>) yamledConfig;
    }
}
