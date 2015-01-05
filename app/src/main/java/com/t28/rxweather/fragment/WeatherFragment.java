package com.t28.rxweather.fragment;

import android.app.Activity;
import android.app.Fragment;
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
import com.t28.rxweather.rx.CoordinateEventBus;
import com.t28.rxweather.rx.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class WeatherFragment extends Fragment {
    @InjectView(R.id.weather_temperature)
    TextView mTemperatureView;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        final EventBus<Coordinate> coordinateEventBus = CoordinateEventBus.Retriever.retrieve();
        AndroidObservable.bindFragment(this, coordinateEventBus.getEventStream())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Coordinate>() {
                    @Override
                    public void call(Coordinate coordinate) {
                        Toast.makeText(activity, coordinate.toString(), Toast.LENGTH_SHORT).show();
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
