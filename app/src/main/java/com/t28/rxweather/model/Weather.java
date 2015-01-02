package com.t28.rxweather.model;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Weather.Builder.class)
public class Weather implements Validatable {
    private static final int NO_CITY_ID = -1;

    private final int mCityId;
    private final String mCityName;
    private final String mCountryCode;

    private final long mSunriseTime;
    private final long mSunsetTime;

    private final Coordinate mCoordinate;
    private final MainAttribute mAttribute;

    private Weather(Builder builder) {
        mCityId = builder.mCityId;
        mCityName = builder.mCityName;
        mCountryCode = builder.mCountryCode;

        mSunriseTime = builder.mSunriseTime;
        mSunsetTime = builder.mSunsetTime;

        mCoordinate = builder.mCoordinate;
        mAttribute = builder.mAttribute;
    }

    @Override
    public boolean isValid() {
        if (mCityId == NO_CITY_ID) {
            return false;
        }

        if (TextUtils.isEmpty(mCityName) || TextUtils.isEmpty(mCountryCode)) {
            return false;
        }

        if (mSunriseTime <= 0 || mSunsetTime <= 0) {
            return false;
        }

        if (mCoordinate == null || !mCoordinate.isValid()) {
            return false;
        }

        if (mAttribute == null || !mAttribute.isValid()) {
            return false;
        }
        return true;
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

    public long getSunriseTime() {
        return mSunriseTime;
    }

    public long getSunsetTime() {
        return mSunsetTime;
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public MainAttribute getAttribute() {
        return mAttribute;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private int mCityId = NO_CITY_ID;
        private String mCityName;
        private String mCountryCode;

        private long mSunriseTime;
        private long mSunsetTime;

        private Coordinate mCoordinate;
        private MainAttribute mAttribute;

        public Builder() {
        }

        @JsonProperty("id")
        public Builder setCityId(int cityId) {
            mCityId = cityId;
            return this;
        }

        @JsonProperty("name")
        public Builder setCityName(String cityName) {
            mCityName = cityName;
            return this;
        }

        public Builder setCountryCode(String countryCode) {
            mCountryCode = countryCode;
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

        @JsonProperty("coord")
        public Builder setCoordinate(Coordinate coordinate) {
            mCoordinate = coordinate;
            return this;
        }

        @JsonProperty("main")
        public Builder setAttribute(MainAttribute attribute) {
            mAttribute = attribute;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
