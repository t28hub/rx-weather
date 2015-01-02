package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = WeatherAttribute.Builder.class)
public class WeatherAttribute {
    private final double mTemperature;
    private final double mMinTemperature;
    private final double mMaxTemperature;

    private final int mHumidity;
    private final int mPressure;

    private WeatherAttribute(Builder builder) {
        mTemperature = builder.mTemperature;
        mMinTemperature = builder.mMinTemperature;
        mMaxTemperature = builder.mMaxTemperature;

        mHumidity = builder.mHumidity;
        mPressure = builder.mPressure;
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

    public int getPressure() {
        return mPressure;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private double mTemperature;
        private double mMinTemperature;
        private double mMaxTemperature;

        private int mHumidity;
        private int mPressure;

        public Builder() {
        }

        @JsonProperty("temp")
        public void setTemperature(double temperature) {
            mTemperature = temperature;
        }

        @JsonProperty("temp_min")
        public void setMinTemperature(double temperature) {
            mMinTemperature = temperature;
        }

        @JsonProperty("temp_max")
        public void setMaxTemperature(double temperature) {
            mMaxTemperature = temperature;
        }

        public void setHumidity(int humidity) {
            mHumidity = humidity;
        }

        public void setPressure(int pressure) {
            mPressure = pressure;
        }

        public WeatherAttribute build() {
            return new WeatherAttribute(this);
        }
    }
}
