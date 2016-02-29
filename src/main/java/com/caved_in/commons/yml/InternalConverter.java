package com.caved_in.commons.yml;


import com.caved_in.commons.yml.converter.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class InternalConverter {
    private LinkedHashSet<YamlConverter> converters = new LinkedHashSet<>();
    private List<Class> customConverters = new ArrayList<>();

    public InternalConverter() {
        try {
            addConverter(PrimitiveYamlConverter.class);
            addConverter(YamlConfigurableConverter.class);
            addConverter(ListYamlConverter.class);
            addConverter(MapYamlConverter.class);
            addConverter(ArrayYamlConverter.class);
            addConverter(SetYamlConverter.class);
        } catch (InvalidConverterException e) {
            throw new IllegalStateException(e);
        }
    }

    public void addConverter(Class converter) throws InvalidConverterException {
        if (!YamlConverter.class.isAssignableFrom(converter)) {
            throw new InvalidConverterException("Class %s does not implement the YamlConverter Interface");
        }

        try {
            YamlConverter converter1 = (YamlConverter) converter.getConstructor(InternalConverter.class).newInstance(this);
            converters.add(converter1);
        } catch (NoSuchMethodException e) {
            throw new InvalidConverterException("converter does not implement a Constructor which takes the InternalConverter instance", e);
        } catch (InvocationTargetException e) {
            throw new InvalidConverterException("converter could not be invoked", e);
        } catch (InstantiationException e) {
            throw new InvalidConverterException("converter could not be instantiated", e);
        } catch (IllegalAccessException e) {
            throw new InvalidConverterException("converter does not implement a public Constructor which takes the InternalConverter instance", e);
        }
    }

    public YamlConverter getConverter(Class type) {
        for (YamlConverter converter : converters) {
            if (converter.supports(type)) {
                return converter;
            }
        }

        return null;
    }

    public void fromConfig(YamlConfigurable configurable, Field field, ConfigSection root, String path) throws Exception {
        Object obj = field.get(configurable);

        YamlConverter converter = null;

        if (obj != null) {
            converter = getConverter(obj.getClass());

            if (converter != null) {
                //Check if we're assigning a static field.
                if (isStaticAssign(field)) {
                    field.set(null, converter.fromConfig(obj.getClass(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                    return;
                }

                field.set(configurable, converter.fromConfig(obj.getClass(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                return;
            } else {
                converter = getConverter(field.getType());
                if (converter != null) {
                    if (isStaticAssign(field)) {
                        field.set(null, converter.fromConfig(field.getType(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                        return;
                    }

                    field.set(configurable, converter.fromConfig(field.getType(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                    return;
                }
            }
        } else {
            converter = getConverter(field.getType());

            if (converter != null) {
                if (isStaticAssign(field)) {
                    field.set(null, converter.fromConfig(field.getType(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                    return;
                }

                field.set(configurable, converter.fromConfig(field.getType(), root.get(path), (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
                return;
            }
        }

        if (isStaticAssign(field)) {
            field.set(null, root.get(path));
            return;
        }
        field.set(configurable, root.get(path));
    }

    public void toConfig(YamlConfigurable config, Field field, ConfigSection root, String path) throws Exception {
        Object obj = field.get(config);

        YamlConverter converter = null;

        if (obj != null) {
            converter = getConverter(obj.getClass());
        }

        if (converter != null) {
            root.set(path, converter.toConfig(obj.getClass(), obj, (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
            return;
        }

        converter = getConverter(field.getType());

        if (converter != null) {
            root.set(path, converter.toConfig(field.getType(), obj, (field.getGenericType() instanceof ParameterizedType) ? (ParameterizedType) field.getGenericType() : null));
            return;
        }

        root.set(path, obj);
    }

    public List<Class> getCustomConverters() {
        return new ArrayList<>(customConverters);
    }

    public void addCustomConverter(Class addConverter) throws InvalidConverterException {
        addConverter(addConverter);
        customConverters.add(addConverter);
    }

    private boolean isStaticAssign(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return false;
        }
        if (!field.isAnnotationPresent(PreserveStatic.class)) {
            return false;
        }

        PreserveStatic staticConfig = field.getAnnotation(PreserveStatic.class);
        if (!staticConfig.value()) {
            return false;
        }
        return true;
    }
}
