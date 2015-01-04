package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.request.WeatherRequest;
import com.t28.rxweather.util.CollectionUtils;
import com.t28.rxweather.volley.RxSupport;

import java.util.Map;

import rx.Observable;

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

    public boolean hasCity() {
        return mCity.isEmpty();
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
        private static final String PROPERTY_COUNTRY_CODE = "country";
        private static final String PROPERTY_SUNRISE = "sunrise";
        private static final String PROPERTY_SUNSET = "sunset";

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
            final Object country = CollectionUtils.getValue(systems, PROPERTY_COUNTRY_CODE, "");
            mCountryCode = country.toString();

            final Object sunrise = CollectionUtils.getValue(systems, PROPERTY_SUNRISE, NO_TIME);
            mSunriseTime = Long.valueOf(sunrise.toString());

            final Object sunset = CollectionUtils.getValue(systems, PROPERTY_SUNSET, NO_TIME);
            mSunsetTime = Long.valueOf(sunset.toString());
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
