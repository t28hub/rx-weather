package com.t28.rxweather.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Weather implements Model {
    public static final float NO_TEMPERATURE = Float.NaN;
    public static final float NO_HUMIDITY = Float.NaN;
    public static final float NO_PRESSURE = Float.NaN;
    public static final long NO_TIME = 0;

    private final float mTemperature;
    private final float mMinTemperature;
    private final float mMaxTemperature;

    private final float mHumidity;
    private final float mPressure;

    private final long mUpdateTime;
    private final long mSunriseTime;
    private final long mSunsetTime;

    private final City mCity;

    private Weather(Builder builder) {
        mTemperature = builder.mTemperature;
        mMinTemperature = builder.mMinTemperature;
        mMaxTemperature = builder.mMaxTemperature;

        mHumidity = builder.mHumidity;
        mPressure = builder.mPressure;

        mUpdateTime = builder.mUpdateTime;
        mSunriseTime = builder.mSunriseTime;
        mSunsetTime = builder.mSunsetTime;

        mCity = builder.mCity;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());

        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonString = mapper.writeValueAsString(this);
            builder.append(jsonString);
        } catch (JsonProcessingException e) {
            builder.append(hashCode());
        }

        return builder.toString();
    }

    @Override
    public boolean isValid() {
        if (mSunriseTime < NO_TIME || mSunsetTime < NO_TIME) {
            return false;
        }

        if (mCity == null) {
            return true;
        }
        return mCity.isValid();
    }

    public float getTemperature() {
        return mTemperature;
    }

    public float getMinTemperature() {
        return mMinTemperature;
    }

    public float getMaxTemperature() {
        return mMaxTemperature;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public float getPressure() {
        return mPressure;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public long getSunriseTime() {
        return mSunriseTime;
    }

    public long getSunsetTime() {
        return mSunsetTime;
    }

    public City getCity() {
        return mCity;
    }

    public boolean hasCity() {
        return mCity != null;
    }

    public static class Builder {
        private float mTemperature = NO_TEMPERATURE;
        private float mMinTemperature = NO_TEMPERATURE;
        private float mMaxTemperature = NO_TEMPERATURE;

        private float mHumidity = NO_HUMIDITY;
        private float mPressure = NO_PRESSURE;

        private long mUpdateTime;
        private long mSunriseTime;
        private long mSunsetTime;

        private City mCity;

        public Builder() {
        }

        public Builder setTemperature(float temperature) {
            mTemperature = temperature;
            return this;
        }

        public Builder setMinTemperature(float temperature) {
            mMinTemperature = temperature;
            return this;
        }

        public Builder setMaxTemperature(float temperature) {
            mMaxTemperature = temperature;
            return this;
        }

        public Builder setHumidity(float humidity) {
            mHumidity = humidity;
            return this;
        }

        public Builder setPressure(float pressure) {
            mPressure = pressure;
            return this;
        }

        public Builder setUpdateTime(long time) {
            mUpdateTime = time;
            return this;
        }

        public Builder setSunriseTime(long time) {
            mSunriseTime = time;
            return this;
        }

        public Builder setSunsetTime(long time) {
            mSunsetTime = time;
            return this;
        }

        public Builder setCity(City city) {
            mCity = city;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
