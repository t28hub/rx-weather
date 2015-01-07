package com.t28.rxweather.data.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class PreferenceConfig {
    private final SharedPreferences mPreferences;

    protected PreferenceConfig(@NonNull Context context, @NonNull String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("'name' must not be empty");
        }
        mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }

    public void clear() {
        mPreferences.edit().clear().apply();
    }
}
