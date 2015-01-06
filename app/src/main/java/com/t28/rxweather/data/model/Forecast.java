package com.t28.rxweather.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Forecast.Builder.class)
public class Forecast implements Model {
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
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(Forecast.class.getSimpleName());

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
        if (mCity == null || !mCity.isValid()) {
            return false;
        }

        for (Weather weather : mWeathers) {
            if (weather.isValid()) {
                continue;
            }
            return false;
        }
        return true;
    }

    public City getCity() {
        return mCity;
    }

    public List<Weather> getWeathers() {
        return new ArrayList<>(mWeathers);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private City mCity;
        private final List<Weather> mWeathers;

        public Builder() {
            mWeathers = new ArrayList<>();
        }

        public Builder setCity(City city) {
            mCity = city;
            return this;
        }

        public Builder addWeather(Weather weather) {
            mWeathers.add(weather);
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }
}
