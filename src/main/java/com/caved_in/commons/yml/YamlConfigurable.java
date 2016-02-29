package com.caved_in.commons.yml;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public interface YamlConfigurable extends IConfig {

    @Override
    default void save() throws InvalidConfigurationException {
        if (getConfigFile() == null) {
            throw new IllegalArgumentException("Saving a config without given File");
        }

        YamledConfiguration config = getYamlConfigurationSettings();

        ConfigSection root = config.getRootConfigSection();

        if (root == null) {
            root = new ConfigSection();
            getYamlConfigurationSettings().setRootConfigSection(root);
        }


        config.clearComments();

        internalSave(getClass());
        config.saveToYaml(getConfigFile());
    }

    /**
     * This function gets called after the File has been loaded and before the converter gets it.
     * This is used to manually edit the configSection when you updated the config or something
     *
     * @param configSection The root ConfigSection with all Subnodes loaded into
     */
    default void update(ConfigSection configSection) {

    }

    @Override
    default void save(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        setConfigFile(file);
        save();
    }

    @Override
    default void init() throws InvalidConfigurationException {
        Yamled.init(this);


        File configFile = getConfigFile();

        if (configFile == null) {
            return;
            //there's no configuration file here.
        }


        if (!getConfigFile().exists()) {
            if (configFile.getParentFile() != null) {
                configFile.getParentFile().mkdirs();
            }

            try {
                configFile.createNewFile();
                save();
            } catch (IOException e) {
                throw new InvalidConfigurationException("Could not create new empty Config", e);
            }
        } else {
            load();
        }
    }

    @Override
    default void init(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        setConfigFile(file);
        init();
    }

    @Override
    default void reload() throws InvalidConfigurationException {
        getYamlConfigurationSettings().loadFromYaml(getConfigFile());
        internalLoad(getClass());
    }

    @Override
    default void load() throws InvalidConfigurationException {
        if (getConfigFile() == null) {
            throw new IllegalArgumentException("Loading a config without given File");
        }

        YamledConfiguration conf = getYamlConfigurationSettings();

        conf.loadFromYaml(getConfigFile());
        update(conf.getRootConfigSection());
        internalLoad(getClass());
    }

    @Override
    default void load(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        setConfigFile(file);
        load();
    }

    default YamledConfiguration<?> getYamlConfigurationSettings() {
        return Yamled.getYamlConfigurationSettings(this);
    }

    default void internalSave(Class<?> clazz) throws InvalidConfigurationException {
        if (!clazz.isAssignableFrom(YamlConfigurable.class)) {
            internalSave(clazz.getSuperclass());
        }

        YamledConfiguration config = getYamlConfigurationSettings();


        InternalConverter converter = config.getConverter();
        ConfigSection root = config.getRootConfigSection();

        for (Field field : clazz.getDeclaredFields()) {
            if (config.doSkip(field)) {
                continue;
            }

            /*
            Retrieve the path of the Field that's being saved.
             */
            String path = config.getPath(field);


            ArrayList<String> comments = new ArrayList<>();
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Comment) {
                    Comment comment = (Comment) annotation;
                    comments.add(comment.value());
                }

                if (annotation instanceof Comments) {
                    Comments comment = (Comments) annotation;
                    comments.addAll(Arrays.asList(comment.value()));
                }
            }

            if (field.isAnnotationPresent(Path.class)) {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if (comments.size() > 0) {
                for (String comment : comments) {
                    config.addComment(path, comment);
                }
            }

            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            try {
                converter.toConfig(this, field, root, path);
                converter.fromConfig(this, field, root, path);
            } catch (Exception e) {
                if (!config.skipFailedObjects()) {
                    throw new InvalidConfigurationException("Could not save the Field", e);
                }
            }
        }
    }

    /**
     * Loads the values from a configuration file, to the given instance of the class.
     * Uses the annotations available inside the {@link YamlConfigurable} class to determine
     * the path which specifies each field variable.
     * <p/>
     * If a value has been changed, and is converted during the process, that value will be reflected
     * in the configuration file to assure there's no missed data.
     *
     * @param clazz class to load the structure from.
     * @throws InvalidConfigurationException
     */
    default void internalLoad(Class<?> clazz) throws InvalidConfigurationException {
        if (!clazz.isAssignableFrom(YamlConfigurable.class)) {
            internalLoad(clazz.getSuperclass());
        }

        YamledConfiguration config = getYamlConfigurationSettings();
        ConfigSection root = config.getRootConfigSection();
        InternalConverter converter = config.getConverter();

        boolean save = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (config.doSkip(field)) {
                continue;
            }

            String path = config.getPath(field);

            if (field.isAnnotationPresent(Path.class)) {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            if (root.has(path)) {
                try {
                    converter.fromConfig(this, field, root, path);
                } catch (Exception e) {
                    throw new InvalidConfigurationException("Could not set field", e);
                }
            } else {
                try {
                    converter.toConfig(this, field, root, path);
                    converter.fromConfig(this, field, root, path);

                    save = true;
                } catch (Exception e) {
                    if (!config.skipFailedObjects()) {
                        throw new InvalidConfigurationException("Could not get field", e);
                    }
                }
            }
        }

        if (save) {
            getYamlConfigurationSettings().saveToYaml(getConfigFile());
        }
    }
}
