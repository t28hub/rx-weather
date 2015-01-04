package com.t28.rxweather.data.service;

import com.android.volley.RequestQueue;
import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Forecast;
import com.t28.rxweather.data.model.Weather;
import com.t28.rxweather.request.ForecastRequest;
import com.t28.rxweather.request.WeatherRequest;
import com.t28.rxweather.volley.RxSupport;

import rx.Observable;

public class WeatherService {
    private final RequestQueue mQueue;

    public WeatherService(RequestQueue queue) {
        mQueue = queue;
    }

    public Observable<Weather> findWeather(Coordinate coordinate) {
        final WeatherRequest request = new WeatherRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return new RxSupport(mQueue).newRequest(request);
    }

    public Observable<Forecast> findForecast(Coordinate coordinate) {
        final ForecastRequest request = new ForecastRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return new RxSupport(mQueue).newRequest(request);
    }
}
