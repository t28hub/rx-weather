package com.t28.rxweather.model;

import com.t28.rxweather.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Forecast implements Validatable {
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
        return false;
    }

    public static class Builder {
        private City mCity;
        private List<Weather> mWeathers;

        public Builder() {
        }

        public Builder setCity(City city) {
            mCity = city;
            return this;
        }

        public Builder setWeathers(List<Weather> weathers) {
            mWeathers = weathers;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }
}
