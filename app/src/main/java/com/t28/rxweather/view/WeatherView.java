package com.t28.rxweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t28.rxweather.R;
import com.t28.rxweather.data.model.Weather;
import com.t28.rxweather.util.WeatherText;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WeatherView extends LinearLayout {
    @InjectView(R.id.weather_temperature)
    TextView mTemperatureView;
    @InjectView(R.id.weather_min_temperature)
    TextView mMinTemperatureView;
    @InjectView(R.id.weather_max_temperature)
    TextView mMaxTemperatureView;
    @InjectView(R.id.weather_pressure)
    TextView mPressureView;
    @InjectView(R.id.weather_humidity)
    TextView mHumidityView;

    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);
    }

    public void update(Weather weather) {
        mTemperatureView.setText(
                WeatherText.toCelsiusString(weather.getTemperature(), true));
        mMinTemperatureView.setText(
                WeatherText.toCelsiusString(weather.getMinTemperature(), true));
        mMaxTemperatureView.setText(
                WeatherText.toCelsiusString(weather.getMaxTemperature(), true));

        mPressureView.setText(String.valueOf((int) weather.getPressure()));
        mHumidityView.setText(
                WeatherText.toHumidityString(weather.getHumidity(), true));
    }
}
