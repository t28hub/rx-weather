package com.t28.rxweather.util;

public class BooleanUtils {
    private static final int INT_TRUE = 1;
    private static final int INT_FALSE = 0;

    private BooleanUtils() {
    }

    public static int toInt(boolean bool) {
        if (bool) {
            return INT_TRUE;
        }
        return INT_FALSE;
    }
}
