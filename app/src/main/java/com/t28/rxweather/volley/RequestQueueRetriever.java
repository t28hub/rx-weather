package com.t28.rxweather.volley;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueRetriever {
    private static RequestQueue sInstance;

    private RequestQueueRetriever() {
    }

    public static synchronized void initialize(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static RequestQueue retrieve() {
        if (sInstance == null) {
            throw new IllegalStateException("RequestQueue has not been initialized");
        }
        return sInstance;
    }
}
