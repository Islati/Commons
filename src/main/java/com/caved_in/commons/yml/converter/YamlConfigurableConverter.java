package com.caved_in.commons.yml.converter;


import com.caved_in.commons.yml.ConfigSection;
import com.caved_in.commons.yml.InternalConverter;
import com.caved_in.commons.yml.YamlConfigurable;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class YamlConfigurableConverter implements YamlConverter {
    private InternalConverter internalConverter;

    public YamlConfigurableConverter(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        if (obj instanceof Map) {
            return obj;
        }

        YamlConfigurable configurable = (YamlConfigurable) obj;
        return configurable.getYamlConfigurationSettings().getConfigurationMap();
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        YamlConfigurable obj = (YamlConfigurable) newInstance(type);


        // Inject converter stack into subconfig
        for (Class aClass : internalConverter.getCustomConverters()) {
            obj.getYamlConfigurationSettings().addConverter(aClass);
        }

        obj.getYamlConfigurationSettings().loadFromConfigurationMap(section instanceof Map ? (Map) section : ((ConfigSection) section).getRawMap());
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
