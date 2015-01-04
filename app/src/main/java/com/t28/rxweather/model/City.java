package com.t28.rxweather.model;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = City.Builder.class)
public class City implements Model {
    public static final int NO_ID = -1;
    public static final String NO_CODE = "";

    private final int mId;
    private final String mName;
    private final String mCountryCode;
    private final Coordinate mCoordinate;

    private City(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mCountryCode = builder.mCountryCode;
        mCoordinate = builder.mCoordinate;
    }

    @Override
    public boolean isValid() {
        if (mId == NO_ID) {
            return false;
        }

        if (TextUtils.isEmpty(mName)) {
            return false;
        }

        if (TextUtils.isEmpty(mCountryCode)) {
            return false;
        }

        if (mCoordinate == null) {
            return false;
        }
        return mCoordinate.isValid();
    }

    public boolean isEmpty() {
        if (mId != NO_ID) {
            return false;
        }

        if (!TextUtils.isEmpty(mName)) {
            return false;
        }

        if (!TextUtils.isEmpty(mCountryCode)) {
            return false;
        }

        if (mCoordinate != null) {
            return false;
        }
        return true;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private int mId = NO_ID;
        private String mName;
        private String mCountryCode;
        private Coordinate mCoordinate;

        public Builder() {
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        @JsonProperty("country")
        public Builder setCountryCode(String code) {
            mCountryCode = code;
            return this;
        }

        @JsonProperty("coord")
        public Builder setCoordinate(Coordinate coordinate) {
            mCoordinate = coordinate;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }
}
