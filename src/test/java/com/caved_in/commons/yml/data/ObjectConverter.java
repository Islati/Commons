package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.InternalConverter;
import com.caved_in.commons.yml.converter.YamlConverter;

import java.lang.reflect.ParameterizedType;

public class ObjectConverter implements YamlConverter {
    public ObjectConverter(InternalConverter internalConverter) {

    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public Object fromConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
}
