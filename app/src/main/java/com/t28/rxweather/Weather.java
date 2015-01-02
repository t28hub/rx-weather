package com.t28.rxweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Weather.Builder.class)
public class Weather {
    private final int mCityId;
    private final String mCityName;
    private final String mCountryCode;

    private final double mLat;
    private final double mLon;

    private final long mSunriseTime;
    private final long mSunsetTime;

    private final double mTemperature;
    private final double mMinTemperature;
    private final double mMaxTemperature;

    private final int mHumidity;

    private Weather(Builder builder) {
        mCityId = builder.mCityId;
        mCityName = builder.mCityName;
        mCountryCode = builder.mCountryCode;

        mLat = builder.mLat;
        mLon = builder.mLon;

        mSunriseTime = builder.mSunriseTime;
        mSunsetTime = builder.mSunsetTime;

        mTemperature = builder.mTemperature;
        mMinTemperature = builder.mMinTemperature;
        mMaxTemperature = builder.mMaxTemperature;

        mHumidity = builder.mHumidity;
    }

    public int getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }

    public long getSunriseTime() {
        return mSunriseTime;
    }

    public long getSunsetTime() {
        return mSunsetTime;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public double getMinTemperature() {
        return mMinTemperature;
    }

    public double getMaxTemperature() {
        return mMaxTemperature;
    }

    public int getHumidity() {
        return mHumidity;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private int mCityId;
        private String mCityName;
        private String mCountryCode;

        private double mLat;
        private double mLon;

        private long mSunriseTime;
        private long mSunsetTime;

        private double mTemperature;
        private double mMinTemperature;
        private double mMaxTemperature;

        private int mHumidity;

        public Builder() {
        }

        public Builder setCityId(int cityId) {
            mCityId = cityId;
            return this;
        }

        public Builder setCityName(String cityName) {
            mCityName = cityName;
            return this;
        }

        public Builder setCountryCode(String countryCode) {
            mCountryCode = countryCode;
            return this;
        }

        public Builder setLat(double lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(double lon) {
            mLon = lon;
            return this;
        }

        public Builder setSunriseTime(long sunriseTime) {
            mSunriseTime = sunriseTime;
            return this;
        }

        public Builder setSunsetTime(long sunsetTime) {
            mSunsetTime = sunsetTime;
            return this;
        }

        public Builder setTemperature(double temperature) {
            mTemperature = temperature;
            return this;
        }

        public Builder setMinTemperature(double minTemperature) {
            mMinTemperature = minTemperature;
            return this;
        }

        public Builder setMaxTemperature(double maxTemperature) {
            mMaxTemperature = maxTemperature;
            return this;
        }

        public Builder setHumidity(int humidity) {
            mHumidity = humidity;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
