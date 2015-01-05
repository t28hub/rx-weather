package com.t28.rxweather.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.t28.rxweather.R;
import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Weather;
import com.t28.rxweather.data.service.WeatherService;
import com.t28.rxweather.rx.CoordinateEventBus;
import com.t28.rxweather.rx.EventBus;
import com.t28.rxweather.volley.RequestQueueRetriever;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WeatherFragment extends Fragment {
    @InjectView(R.id.weather_temperature)
    TextView mTemperatureView;

    private WeatherService mWeatherService;

    public WeatherFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        mWeatherService = new WeatherService(RequestQueueRetriever.retrieve());

        final EventBus<Coordinate> coordinateEventBus = CoordinateEventBus.Retriever.retrieve();
        AndroidObservable.bindFragment(this, coordinateEventBus.getEventStream())
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .flatMap(new Func1<Coordinate, Observable<Weather>>() {
                    @Override
                    public Observable<Weather> call(Coordinate coordinate) {
                        return mWeatherService.findWeather(coordinate);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable cause) {
                        Toast.makeText(activity, cause.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Weather result) {
                        Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final String degree = Html.fromHtml("&deg;").toString();
        final String temperature = new StringBuilder().append(10).append(degree).toString();
        mTemperatureView.setText(temperature);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
