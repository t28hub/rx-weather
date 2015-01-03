package com.t28.rxweather.model;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.request.WeatherRequest;
import com.t28.rxweather.util.CollectionUtils;
import com.t28.rxweather.volley.RxSupport;

import java.util.Map;

import rx.Observable;

@JsonDeserialize(builder = Weather.Builder.class)
public class Weather implements Validatable {
    public static final int NO_CITY_ID = -1;

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
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName());

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
            builder.append(mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            builder.append(super.toString());
        }

        return builder.toString();
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

    public static Observable<Weather> findByCityName(RxSupport support, String name) {
        final WeatherRequest request = new WeatherRequest.Builder("")
                .setCityName(name)
                .build();
        return support.createObservableRequest(request);
    }

    public static Observable<Weather> findByCityId(RxSupport support, int id) {
        final WeatherRequest request = new WeatherRequest.Builder("")
                .setCityId(id)
                .build();
        return support.createObservableRequest(request);
    }

    public static Observable<Weather> findByCoordinate(RxSupport support, Coordinate coordinate) {
        final WeatherRequest request = new WeatherRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return support.createObservableRequest(request);
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

        @JsonProperty("sys")
        public Builder setSystem(Map<String, Object> systems) {
            mCountryCode = CollectionUtils.getValue(systems, "country", "").toString();
            mSunriseTime = Long.valueOf(CollectionUtils.getValue(systems, "sunrise", 0).toString());
            mSunsetTime = Long.valueOf(CollectionUtils.getValue(systems, "sunset", 0).toString());
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
