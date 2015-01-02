package com.t28.rxweather;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import rx.Observable;
import rx.Subscriber;

public class Coordinate {
    private final double mLat;
    private final double mLon;

    public Coordinate(double lat, double lon) {
        mLat = lat;
        mLon = lon;
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
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

            final Coordinate coordinate = new Coordinate(
                    location.getLatitude(),
                    location.getLongitude()
            );
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
