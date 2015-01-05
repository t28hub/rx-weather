package com.t28.rxweather.util;

import android.text.Html;

public class TemperatureConverter {
    private static final float CELSIUS = 272.15f;
    private static final String DEGREE = Html.fromHtml("&deg;").toString();

    private TemperatureConverter() {
    }

    public static String toCelsiusString(float kelvin, boolean hasSymbol) {
        final StringBuilder builder = new StringBuilder();
        builder.append(Math.round(kelvin - CELSIUS));
        if (hasSymbol) {
            builder.append(DEGREE);
        }
        return builder.toString();
    }
}
