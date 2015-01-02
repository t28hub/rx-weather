package com.t28.rxweather;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueRetriever {
    private static RequestQueue sInstance;

    private RequestQueueRetriever() {
    }

    public static synchronized RequestQueue retrieve(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = Volley.newRequestQueue(context.getApplicationContext());
        }
        return sInstance;
    }
}
