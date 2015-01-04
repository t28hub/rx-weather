package com.t28.rxweather.data.model;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import rx.Subscriber;

@JsonDeserialize(builder = Coordinate.Builder.class)
public class Coordinate implements Model {
    public static final float NO_LAT = Float.NaN;
    public static final float NO_LON = Float.NaN;

    private final float mLat;
    private final float mLon;

    private Coordinate(Builder builder) {
        mLat = builder.mLat;
        mLon = builder.mLon;
    }

    @Override
    public boolean isValid() {
        if (Float.isNaN(mLat)) {
            return false;
        }

        if (Float.isNaN(mLon)) {
            return false;
        }
        return true;
    }

    public float getLat() {
        return mLat;
    }

    public float getLon() {
        return mLon;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private float mLat = NO_LAT;
        private float mLon = NO_LON;

        public Builder() {
        }

        public Builder setLat(float lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(float lon) {
            mLon = lon;
            return this;
        }

        public Coordinate build() {
            return new Coordinate(this);
        }
    }


}
