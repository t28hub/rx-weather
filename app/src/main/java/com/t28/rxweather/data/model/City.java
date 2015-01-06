package com.t28.rxweather.data.model;

import android.text.TextUtils;

public class City implements Model {
    public static final int NO_ID = -1;

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

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

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

        public Builder setCountryCode(String code) {
            mCountryCode = code;
            return this;
        }

        public Builder setCoordinate(Coordinate coordinate) {
            mCoordinate = coordinate;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }
}
