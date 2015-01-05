package com.t28.rxweather;

import android.app.Application;

import com.t28.rxweather.volley.RequestQueueRetriever;

public class RxWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestQueueRetriever.initialize(this);
    }
}
