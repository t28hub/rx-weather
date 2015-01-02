package com.t28.rxweather.volley;

import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.t28.rxweather.Validatable;

import rx.Observable;
import rx.Subscriber;

public class RxSupport {
    private final RequestQueue mQueue;

    public RxSupport(@NonNull RequestQueue queue) {
        mQueue = queue;
    }

    public <T extends Validatable> Observable<T> createObservableRequest(ListenableRequest<T> request) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

            }
        });
    }
}
