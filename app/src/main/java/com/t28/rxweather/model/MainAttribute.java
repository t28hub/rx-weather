package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = MainAttribute.Builder.class)
public class MainAttribute implements Validatable {
    private static final double NO_TEMPERATURE = Double.NaN;
    private static final double NO_HUMIDITY = Double.NaN;
    private static final double NO_PRESSURE = Double.NaN;

    private final double mTemperature;
    private final double mMinTemperature;
    private final double mMaxTemperature;

    private final double mHumidity;
    private final double mPressure;

    private MainAttribute(Builder builder) {
        mTemperature = builder.mTemperature;
        mMinTemperature = builder.mMinTemperature;
        mMaxTemperature = builder.mMaxTemperature;

        mHumidity = builder.mHumidity;
        mPressure = builder.mPressure;
    }

    @Override
    public boolean isValid() {
        if (Double.isNaN(mTemperature) ||
                Double.isNaN(mMinTemperature) ||
                Double.isNaN(mMaxTemperature)) {
            return false;
        }

        if (Double.isNaN(mHumidity)) {
            return false;
        }

        if (Double.isNaN(mPressure)) {
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
        private double mTemperature = NO_TEMPERATURE;
        private double mMinTemperature = NO_TEMPERATURE;
        private double mMaxTemperature = NO_TEMPERATURE;

        private double mHumidity = NO_HUMIDITY;
        private double mPressure = NO_PRESSURE;

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

        public void setHumidity(double humidity) {
            mHumidity = humidity;
        }

        public void setPressure(double pressure) {
            mPressure = pressure;
        }

        public MainAttribute build() {
            return new MainAttribute(this);
        }
    }
}
