package com.t28.rxweather.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        return list.isEmpty();
    }

    public static <T> List<T> newList(Iterator<T> iterator) {
        if (iterator == null) {
            return Collections.emptyList();
        }

        final List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            final T element = iterator.next();
            list.add(element);
        }
        return list;
    }

    public static <T1, T2> T2 getValue(Map<T1, T2> map, T1 key, T2 defaultValue) {
        if (map == null) {
            return defaultValue;
        }

        final T2 value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static long getValueAsLong(Map map, Object key, long defaultValue) {
        final Object value = getValue(map, key, null);
        if (value == null) {
            return defaultValue;
        }
        return Long.valueOf(value.toString());
    }

    public static String getValueAsString(Map map, Object key, String defaultValue) {
        final Object value = getValue(map, key, null);
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }
}
