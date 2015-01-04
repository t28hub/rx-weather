package com.t28.rxweather.model;

import com.android.volley.RequestQueue;
import com.t28.rxweather.request.PhotosSearchRequest;
import com.t28.rxweather.volley.RxSupport;

import java.util.Arrays;

import rx.Observable;

public class FlickerService {
    private static final int DEFAULT_RADIUS = 30;
    private static final String DEFAULT_SEARCH_TAG = "Weather";

    private final RequestQueue mQueue;

    public FlickerService(RequestQueue queue) {
        mQueue = queue;
    }

    public Observable<Photos> searchPhotos(Coordinate coordinate) {
        final PhotosSearchRequest request = new PhotosSearchRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .setTags(Arrays.asList(DEFAULT_SEARCH_TAG))
                .setRadius(DEFAULT_RADIUS)
                .setRadiusUnit(PhotosSearchRequest.Builder.RADIUS_UNITS_KM)
                .setFormat(PhotosSearchRequest.Builder.FORMAT_JSON)
                .setNoJsonCallback(true)
                .build();
        return new RxSupport(mQueue).createObservableRequest(request);
    }
}
