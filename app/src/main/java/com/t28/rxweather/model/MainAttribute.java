package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = MainAttribute.Builder.class)
public class MainAttribute implements Model {
    public static final float NO_TEMPERATURE = Float.NaN;
    public static final float NO_HUMIDITY = Float.NaN;
    public static final float NO_PRESSURE = Float.NaN;

    private final float mTemperature;
    private final float mMinTemperature;
    private final float mMaxTemperature;

    private final float mHumidity;
    private final float mPressure;

    private MainAttribute(Builder builder) {
        mTemperature = builder.mTemperature;
        mMinTemperature = builder.mMinTemperature;
        mMaxTemperature = builder.mMaxTemperature;

        mHumidity = builder.mHumidity;
        mPressure = builder.mPressure;
    }

    @Override
    public boolean isValid() {
        if (Float.isNaN(mTemperature) ||
                Float.isNaN(mMinTemperature) ||
                Float.isNaN(mMaxTemperature)) {
            return false;
        }

        if (Float.isNaN(mHumidity)) {
            return false;
        }

        if (Float.isNaN(mPressure)) {
            return false;
        }
        return true;
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

    public double getHumidity() {
        return mHumidity;
    }

    public double getPressure() {
        return mPressure;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private float mTemperature = NO_TEMPERATURE;
        private float mMinTemperature = NO_TEMPERATURE;
        private float mMaxTemperature = NO_TEMPERATURE;

        private float mHumidity = NO_HUMIDITY;
        private float mPressure = NO_PRESSURE;

        public Builder() {
        }

        @JsonProperty("temp")
        public void setTemperature(float temperature) {
            mTemperature = temperature;
        }

        @JsonProperty("temp_min")
        public void setMinTemperature(float temperature) {
            mMinTemperature = temperature;
        }

        @JsonProperty("temp_max")
        public void setMaxTemperature(float temperature) {
            mMaxTemperature = temperature;
        }

        public void setHumidity(float humidity) {
            mHumidity = humidity;
        }

        public void setPressure(float pressure) {
            mPressure = pressure;
        }

        public MainAttribute build() {
            return new MainAttribute(this);
        }
    }
}
