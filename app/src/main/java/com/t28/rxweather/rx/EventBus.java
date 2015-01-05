package com.t28.rxweather.rx;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.subjects.PublishSubject;

public class EventBus<T> {
    private final PublishSubject<T> mEventSubject;
    private final Observable<T> mEventStream;

    public EventBus() {
        mEventSubject = PublishSubject.create();
        mEventStream = mEventSubject.serialize();
    }

    public EventBus(@NonNull PublishSubject<T> subject) {
        mEventSubject = subject;
        mEventStream = mEventSubject.serialize();
    }

    public Observable<T> getEventStream() {
        return mEventStream;
    }

    public void publish(T event) {
        mEventSubject.onNext(event);
    }
}
