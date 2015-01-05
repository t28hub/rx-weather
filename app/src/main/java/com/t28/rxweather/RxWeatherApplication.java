package com.t28.rxweather;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.volley.VolleyUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.t28.rxweather.volley.RequestQueueRetriever;

import java.io.InputStream;

public class RxWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RequestQueueRetriever.initialize(this);
        final RequestQueue queue = RequestQueueRetriever.retrieve();
        final VolleyUrlLoader.Factory factory = new VolleyUrlLoader.Factory(queue);
        Glide.get(this).register(GlideUrl.class, InputStream.class, factory);
    }

    @Override
    public void onTerminate() {
        Glide.get(this).unregister(GlideUrl.class, InputStream.class);

        super.onTerminate();
    }
}
