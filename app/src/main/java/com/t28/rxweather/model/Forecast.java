package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.request.ForecastRequest;
import com.t28.rxweather.util.CollectionUtils;
import com.t28.rxweather.volley.RxSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

@JsonDeserialize(builder = Forecast.Builder.class)
public class Forecast extends Model {
    private final City mCity;
    private final List<Weather> mWeathers;

    private Forecast(Builder builder) {
        mCity = builder.mCity;
        if (CollectionUtils.isEmpty(builder.mWeathers)) {
            mWeathers = Collections.emptyList();
        } else {
            mWeathers = new ArrayList<>(builder.mWeathers);
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public boolean isEmpty() {
        return true;
    }

    public City getCity() {
        return mCity;
    }

    public List<Weather> getWeathers() {
        return new ArrayList<>(mWeathers);
    }

    public static Observable<Forecast> findByName(RxSupport support, String name) {
        final ForecastRequest request = new ForecastRequest.Builder("")
                .setCityName(name)
                .build();
        return support.createObservableRequest(request);
    }

    public static Observable<Forecast> findByCoordinate(RxSupport support, Coordinate coordinate) {
        final ForecastRequest request = new ForecastRequest.Builder("")
                .setLat(coordinate.getLat())
                .setLon(coordinate.getLon())
                .build();
        return support.createObservableRequest(request);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private City mCity;
        private List<Weather> mWeathers;

        public Builder() {
        }

        public Builder setCity(City city) {
            mCity = city;
            return this;
        }

        @JsonProperty("list")
        public Builder setWeathers(List<Weather> weathers) {
            mWeathers = weathers;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }
}
