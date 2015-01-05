package com.t28.rxweather.data.service;

import com.android.volley.RequestQueue;
import com.t28.rxweather.api.request.ForecastRequest;
import com.t28.rxweather.api.request.WeatherRequest;
import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Forecast;
import com.t28.rxweather.data.model.Weather;
import com.t28.rxweather.volley.RxSupport;

import rx.Observable;

public class WeatherService {
    private final String mApiKey;
    private final RequestQueue mQueue;

    public WeatherService(String apiKey, RequestQueue queue) {
        mApiKey = apiKey;
        mQueue = queue;
    }

    public Observable<Weather> findWeather(Coordinate coordinate) {
        final WeatherRequest request = new WeatherRequest.Builder(mApiKey)
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return RxSupport.newRequest(mQueue, request);
    }

    public Observable<Forecast> findForecast(Coordinate coordinate) {
        final ForecastRequest request = new ForecastRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return RxSupport.newRequest(mQueue, request);
    }
}
