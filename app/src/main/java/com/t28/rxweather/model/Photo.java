package com.t28.rxweather.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class Photo extends Model {
    private static final String FORMAT_IMAGE_URL = "https://farm%d.staticflickr.com/%d/%d_%s_%s.jpg";
    private static final String FORMAT_PHOTO_URL = "https://www.flickr.com/photos/%s/%d";

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
        if (mId < 0) {
            return false;
        }

        if (mFarmId < 0) {
            return false;
        }

        if (mServerId < 0) {
            return false;
        }

        if (TextUtils.isEmpty(mUserId)) {
            return false;
        }

        if (TextUtils.isEmpty(mSecret)) {
            return false;
        }
        return true;
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

    public Uri toImageUri(@NonNull PhotoSize size) {
        final String urlString = String.format(
                FORMAT_IMAGE_URL, mFarmId, mServerId, mId, mSecret, size.toSuffix()
        );
        return Uri.parse(urlString);
    }

    public Uri toPhotoUri() {
        final String urlString = String.format(
                FORMAT_PHOTO_URL, mUserId, mId
        );
        return Uri.parse(urlString);
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
