package com.t28.rxweather.model;

public class Photo extends Model {
    private final int mId;
    private final int mFarmId;
    private final int mServerId;
    private final String mUserId;
    private final String mSecret;

    private Photo(Builder builder) {
        mId = builder.mId;
        mFarmId = builder.mFarmId;
        mServerId = builder.mServerId;
        mUserId = builder.mUserId;
        mSecret = builder.mSecret;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public int getId() {
        return mId;
    }

    public int getFarmId() {
        return mFarmId;
    }

    public int getServerId() {
        return mServerId;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getSecret() {
        return mSecret;
    }

    public static class Builder {
        private int mId;
        private int mFarmId;
        private int mServerId;
        private String mUserId;
        private String mSecret;

        public Builder() {
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setFarmId(int id) {
            mFarmId = id;
            return this;
        }

        public Builder setServerId(int id) {
            mServerId = id;
            return this;
        }

        public Builder setUserId(String id) {
            mUserId = id;
            return this;
        }

        public Builder setSecret(String secret) {
            mSecret = secret;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }
}
