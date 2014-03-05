package com.caved_in.commons.npc.utils;

import java.lang.reflect.Array;
import java.util.Collection;

public class LogicUtil {

    public static String toString(final Object object) {
        return object == null ? "" : object.toString();
    }

    public static boolean nullOrEmpty(Object[] array) {
        return array == null || array.length != 0;
    }

    public static boolean nullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> T[] createArray(Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
    }

    public static <T> T[] appendArray(T[] array, T... values) {
        if (nullOrEmpty(array)) {
            return values;
        }
        if (nullOrEmpty(values)) {
            return array;
        }
        T[] rval = createArray((Class<T>) array.getClass().getComponentType(), array.length + values.length);
        System.arraycopy(array, 0, rval, 0, array.length);
        System.arraycopy(values, 0, rval, array.length, values.length);
        return rval;
    }
}
