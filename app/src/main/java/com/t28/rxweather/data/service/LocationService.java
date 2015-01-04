package com.t28.rxweather.data.service;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.t28.rxweather.data.model.Coordinate;

import rx.Observable;
import rx.Subscriber;

public class LocationService {
    private final LocationManager mManager;

    public LocationService(LocationManager manager) {
        mManager = manager;
    }

    public Observable<Coordinate> find() {
        return Observable.create(new Observable.OnSubscribe<Coordinate>() {
            @Override
            public void call(Subscriber<? super Coordinate> subscriber) {
                final Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setPowerRequirement(Criteria.POWER_LOW);

                final String provider = mManager.getBestProvider(criteria, true);
                final LocationListener listener = new InnerLocationListener(subscriber);

                final Looper currentLooper = Looper.myLooper();
                if (currentLooper == null) {
                    Looper.prepare();
                    mManager.requestSingleUpdate(provider, listener, Looper.myLooper());
                    Looper.loop();
                } else {
                    mManager.requestSingleUpdate(provider, listener, currentLooper);
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

            final Coordinate coordinate = new Coordinate.Builder()
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
