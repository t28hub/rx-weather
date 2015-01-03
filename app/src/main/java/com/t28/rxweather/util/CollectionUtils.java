package com.t28.rxweather.util;

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
}
