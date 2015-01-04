package com.t28.rxweather.data.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Photo.Builder.class)
public class Photo implements Model {
    private static final String FORMAT_IMAGE_URL = "https://farm%d.staticflickr.com/%d/%d_%s_%s.jpg";
    private static final String FORMAT_PHOTO_URL = "https://www.flickr.com/photos/%s/%d";

    private final long mId;
    private final int mFarmId;
    private final int mServerId;
    private final String mUserId;
    private final String mSecret;
    private final String mTitle;

    private Photo(Builder builder) {
        mId = builder.mId;
        mFarmId = builder.mFarmId;
        mServerId = builder.mServerId;
        mUserId = builder.mUserId;
        mSecret = builder.mSecret;
        mTitle = builder.mTitle;
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

        if (TextUtils.isEmpty(mTitle)) {
            return false;
        }
        return true;
    }

    public long getId() {
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

    public String getTitle() {
        return mTitle;
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

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long mId;
        private int mFarmId;
        private int mServerId;
        private String mUserId;
        private String mSecret;
        private String mTitle;

        public Builder() {
        }

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        @JsonProperty("farm")
        public Builder setFarmId(int id) {
            mFarmId = id;
            return this;
        }

        @JsonProperty("server")
        public Builder setServerId(int id) {
            mServerId = id;
            return this;
        }

        @JsonProperty("owner")
        public Builder setUserId(String id) {
            mUserId = id;
            return this;
        }

        public Builder setSecret(String secret) {
            mSecret = secret;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }
}
