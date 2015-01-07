package com.t28.rxweather.data.config;

import android.content.Context;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WeatherConfig extends PreferenceConfig {
    private static final String KEY_REGISTERED_CODES = "registered_codes";

    public WeatherConfig(Context context) {
        super(context, WeatherConfig.class.getCanonicalName());
    }

    public Set<String> loadRegisteredCodes() {
        final Set<String> defaultValues = Collections.emptySet();
        return loadStrings(KEY_REGISTERED_CODES, defaultValues);
    }

    public void addRegisteredCode(String code) {
        Set<String> codes = loadStrings(KEY_REGISTERED_CODES, null);
        if (codes == null) {
            codes = new HashSet<>();
        }
        codes.add(code);
        saveStringSet(KEY_REGISTERED_CODES, codes);
    }
}
