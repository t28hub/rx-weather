package com.t28.rxweather.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.util.CollectionUtils;

import java.util.Map;

@JsonDeserialize(builder = Weather.Builder.class)
public class Weather implements Model {
    public static final long NO_TIME = 0;

    private final MainAttribute mAttribute;
    private final long mSunriseTime;
    private final long mSunsetTime;
    private final City mCity;

    private Weather(Builder builder) {
        mAttribute = builder.mAttribute;
        mSunriseTime = builder.mSunriseTime;
        mSunsetTime = builder.mSunsetTime;
        mCity = new City.Builder()
                .setId(builder.mCityId)
                .setName(builder.mCityName)
                .setCountryCode(builder.mCountryCode)
                .setCoordinate(builder.mCoordinate)
                .build();
    }

    @Override
    public boolean isValid() {
        if (mSunriseTime < NO_TIME || mSunsetTime < NO_TIME) {
            return false;
        }

        if (mAttribute == null || !mAttribute.isValid()) {
            return false;
        }

        if (mCity.isEmpty()) {
            return true;
        }
        return mCity.isValid();
    }

    public MainAttribute getAttribute() {
        return mAttribute;
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
        return mCity.isEmpty();
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private static final String KEY_COUNTRY_CODE = "country";
        private static final String KEY_SUNRISE = "sunrise";
        private static final String KEY_SUNSET = "sunset";

        private int mCityId = City.NO_ID;
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

        public Builder setCountryCode(String code) {
            mCountryCode = code;
            return this;
        }

        @JsonProperty("sys")
        public Builder setSystem(Map<String, Object> systems) {
            mCountryCode = CollectionUtils.getValueAsString(systems, KEY_COUNTRY_CODE, null);
            mSunriseTime = CollectionUtils.getValueAsLong(systems, KEY_SUNRISE, NO_TIME);
            mSunsetTime = CollectionUtils.getValueAsLong(systems, KEY_SUNSET, NO_TIME);
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
