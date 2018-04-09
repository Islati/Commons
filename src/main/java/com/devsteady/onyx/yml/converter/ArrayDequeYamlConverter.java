package com.devsteady.onyx.yml.converter;


import com.devsteady.onyx.yml.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayDeque;

/**
 * @author TheGamersCave
 */
public class ArrayDequeYamlConverter implements Converter {
    private InternalConverter internalConverter;

    public ArrayDequeYamlConverter(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
        ArrayDeque values = (ArrayDeque) obj;
        ArrayDeque newDeque = new ArrayDeque();

        for (Object val : values) {
            Converter converter = internalConverter.getConverter(val.getClass());

            if (converter != null) {
                newDeque.add(converter.toConfig(val.getClass(), val, null));
            } else {
                newDeque.add(val);
            }
        }

        return newDeque;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        ArrayDeque newDeque = new ArrayDeque();

        try {
            newDeque = ((ArrayDeque) type.newInstance());
        } catch (Exception e) {

        }

        ArrayDeque values = (ArrayDeque) section;

        if (genericType != null && genericType.getActualTypeArguments()[0] instanceof Class) {
            Converter converter = internalConverter.getConverter((Class) genericType.getActualTypeArguments()[0]);

            if (converter != null) {
                for (Object value : values) {
                    newDeque.add(converter.fromConfig((Class) genericType.getActualTypeArguments()[0], value, null));
                }
            } else {
                newDeque = values;
            }
        } else {
            newDeque = values;
        }

        return newDeque;
    }

    @Override
    public boolean supports(Class<?> type) {
        return ArrayDeque.class.isAssignableFrom(type);
    }
}
