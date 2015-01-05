package com.t28.rxweather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t28.rxweather.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WeatherFragment extends Fragment {
    @InjectView(R.id.weather_temperature)
    TextView mTemperatureView;

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
