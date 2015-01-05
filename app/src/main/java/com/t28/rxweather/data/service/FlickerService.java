package com.t28.rxweather.data.service;

import com.android.volley.RequestQueue;
import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Photos;
import com.t28.rxweather.api.request.PhotosSearchRequest;
import com.t28.rxweather.volley.RxSupport;

import java.util.Arrays;

import rx.Observable;

public class FlickerService {
    private static final int DEFAULT_RADIUS = 20;

    private final String mApiKey;
    private final RequestQueue mQueue;

    public FlickerService(String apiKey, RequestQueue queue) {
        mApiKey = apiKey;
        mQueue = queue;
    }

    public Observable<Photos> searchPhotos(Coordinate coordinate) {
        final PhotosSearchRequest request = new PhotosSearchRequest.Builder(mApiKey)
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .setTags(Arrays.asList("weather", "climate"))
                .setRadius(DEFAULT_RADIUS)
                .setRadiusUnit(PhotosSearchRequest.Builder.RADIUS_UNITS_KM)
                .setFormat(PhotosSearchRequest.Builder.FORMAT_JSON)
                .setNoJsonCallback(true)
                .build();
        return RxSupport.newRequest(mQueue, request);
    }
}
