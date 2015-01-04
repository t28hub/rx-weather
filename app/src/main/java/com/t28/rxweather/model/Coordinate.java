package com.t28.rxweather.model;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import rx.Observable;
import rx.Subscriber;

@JsonDeserialize(builder = Coordinate.Builder.class)
public class Coordinate implements Model {
    public static final float NO_LAT = Float.NaN;
    public static final float NO_LON = Float.NaN;

    private final float mLat;
    private final float mLon;

    private Coordinate(Builder builder) {
        mLat = builder.mLat;
        mLon = builder.mLon;
    }

    @Override
    public boolean isValid() {
        if (Float.isNaN(mLat)) {
            return false;
        }

        if (Float.isNaN(mLon)) {
            return false;
        }
        return true;
    }

    public float getLat() {
        return mLat;
    }

    public float getLon() {
        return mLon;
    }

    public static Observable<Coordinate> find(final LocationManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("'manager' must not be null");
        }

        return Observable.create(new Observable.OnSubscribe<Coordinate>() {
            @Override
            public void call(Subscriber<? super Coordinate> subscriber) {
                final Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setPowerRequirement(Criteria.POWER_LOW);

                final String provider = manager.getBestProvider(criteria, true);
                final LocationListener listener = new InnerLocationListener(subscriber);

                final Looper currentLooper = Looper.myLooper();
                if (currentLooper == null) {
                    Looper.prepare();
                    manager.requestSingleUpdate(provider, listener, Looper.myLooper());
                    Looper.loop();
                } else {
                    manager.requestSingleUpdate(provider, listener, currentLooper);
                }
            }
        });
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private float mLat = NO_LAT;
        private float mLon = NO_LON;

        public Builder() {
        }

        public Builder setLat(float lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(float lon) {
            mLon = lon;
            return this;
        }

        public Coordinate build() {
            return new Coordinate(this);
        }
    }

    private static class InnerLocationListener implements LocationListener {
        private final Subscriber<? super Coordinate> mSubscriber;

        public InnerLocationListener(Subscriber<? super Coordinate> subscriber) {
            mSubscriber = subscriber;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (mSubscriber.isUnsubscribed()) {
                return;
            }

            final Coordinate coordinate = new Builder()
                    .setLat((float) location.getLatitude())
                    .setLon((float) location.getLongitude())
                    .build();
            mSubscriber.onNext(coordinate);
            mSubscriber.onCompleted();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
