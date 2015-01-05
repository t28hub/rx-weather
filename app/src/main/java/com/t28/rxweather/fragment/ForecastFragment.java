package com.t28.rxweather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Forecast;
import com.t28.rxweather.data.service.WeatherService;
import com.t28.rxweather.rx.CoordinateEventBus;
import com.t28.rxweather.volley.RequestQueueRetriever;

import rx.Observable;
import rx.Observer;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ForecastFragment extends Fragment {
    private WeatherService mWeatherService;

    public ForecastFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeatherService = new WeatherService("", RequestQueueRetriever.retrieve());
        AndroidObservable.bindFragment(this, createForecastObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Forecast>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable cause) {
                        onFailure(cause);
                    }

                    @Override
                    public void onNext(Forecast result) {
                        onSuccess(result);
                    }
                });
    }

    private void onSuccess(Forecast result) {
        if (isDetached()) {
            return;
        }

        Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
    }

    private void onFailure(Throwable cause) {
        if (isDetached()) {
            return;
        }

        Toast.makeText(getActivity(), cause.toString(), Toast.LENGTH_SHORT).show();
    }

    private Observable<Forecast> createForecastObservable() {
        return CoordinateEventBus.Retriever.retrieve()
                .getEventStream()
                .flatMap(new Func1<Coordinate, Observable<Forecast>>() {
                    @Override
                    public Observable<Forecast> call(Coordinate coordinate) {
                        return mWeatherService.findForecast(coordinate);
                    }
                });
    }
}
