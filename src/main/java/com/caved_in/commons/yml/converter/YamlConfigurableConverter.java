package com.caved_in.commons.yml.converter;


import com.caved_in.commons.yml.*;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class YamlConfigurableConverter implements YamlConverter {
    private InternalConverter internalConverter;

    public YamlConfigurableConverter(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        if (obj == null) {
            throw new InvalidConfigurationException("Unable to serialize a null object.");
        }

        if (obj instanceof Map) {
            return obj;
        }

        if (!(obj instanceof YamlConfigurable)) {
            throw new InvalidConverterException(String.format("Unable to convert object of type %s with YamlConfigurableConverter", obj.getClass().getCanonicalName()));
        }

        YamlConfigurable configurable = (YamlConfigurable) obj;

        Map<String, Object> configMap = null;
        try {
            configMap = configurable.getYamlConfigurationSettings().getConfigurationMap(configurable);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (configMap == null) {
            throw new InvalidConfigurationException(String.format("Invalid (null) configuration map for class %s", type.getCanonicalName()));
        }


        return configMap;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        YamlConfigurable obj = (YamlConfigurable) newInstance(type);

        if (obj == null) {
            throw new IllegalAccessError("Unable to initialize class %s as YamlConfigurable");
        }

        /*
        After we attempt to create the object from direct configuration, we HAVE to initialize it in Yamld.
         */
        Yamled.init(obj);

        // Inject converter stack into subconfig
        for (Class aClass : internalConverter.getCustomConverters()) {
            obj.getYamlConfigurationSettings().addConverter(aClass);
        }

        YamledConfiguration config = obj.getYamlConfigurationSettings();

        if (section instanceof Map) {
            config.loadFromConfigurationMap(obj, (Map) section);
        } else if (section instanceof ConfigSection) {
            config.loadFromConfigurationMap(obj, ((ConfigSection) section).getRawMap());
        } else {
            System.out.println(String.format("Unable to parse section of type %s... Class is %s", section.getClass().getCanonicalName(), type.getCanonicalName()));
            return null;
        }

        return obj;
    }

    // recursively handles enclosed classes
    public Object newInstance(Class type) throws Exception {
        Class enclosingClass = type.getEnclosingClass();
        if (enclosingClass != null) {
            Object instanceOfEnclosingClass = newInstance(enclosingClass);
            return type.getConstructor(enclosingClass).newInstance(instanceOfEnclosingClass);
        } else {
            return type.newInstance();
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return YamlConfigurable.class.isAssignableFrom(type);
    }
}
