package com.t28.rxweather.util;

import android.text.Html;

public class WeatherText {
    private static final float CELSIUS = 272.15f;
    private static final String DEGREE = Html.fromHtml("&deg;").toString();
    private static final String PERCENT = "%";

    private WeatherText() {
    }

    public static String toCelsiusString(float kelvin, boolean hasSymbol) {
        final StringBuilder builder = new StringBuilder();
        builder.append(Math.round(kelvin - CELSIUS));
        if (hasSymbol) {
            builder.append(DEGREE);
        }
        return builder.toString();
    }

    public static String toHumidityString(float humidity, boolean hasSymbol) {
        final StringBuilder builder = new StringBuilder();
        builder.append(Math.round(humidity));
        if (hasSymbol) {
            builder.append(PERCENT);
        }
        return builder.toString();
    }
}
