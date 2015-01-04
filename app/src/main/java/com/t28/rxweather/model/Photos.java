package com.t28.rxweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.t28.rxweather.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Photos.Builder.class)
public class Photos extends Model {
    private final int mPage;
    private final int mPageCount;
    private final int mPerPage;
    private final int mTotal;
    private final List<Photo> mPhotos;

    private Photos(Builder builder) {
        mPage = builder.mPage;
        mPageCount = builder.mPageCount;
        mPerPage = builder.mPerPage;
        mTotal = builder.mTotal;
        if (CollectionUtils.isEmpty(builder.mPhotos)) {
            mPhotos = Collections.emptyList();
        } else {
            mPhotos = new ArrayList<>(builder.mPhotos);
        }
    }

    @Override
    public boolean isValid() {
        if (mPage < 0 || mPage > mPageCount) {
            return false;
        }

        if (mPageCount < 0) {
            return false;
        }

        if (mPerPage < 0) {
            return false;
        }

        if (mTotal < 0) {
            return false;
        }
        return true;
    }

    public int getPage() {
        return mPage;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public int getTotal() {
        return mTotal;
    }

    public int count() {
        return mPhotos.size();
    }

    public List<Photo> getPhotos() {
        return new ArrayList<>(mPhotos);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private int mPage;
        private int mPageCount;
        private int mPerPage;
        private int mTotal;
        private List<Photo> mPhotos;

        public Builder() {
        }

        public Builder setPage(int page) {
            mPage = page;
            return this;
        }

        @JsonProperty("pages")
        public Builder setPageCount(int count) {
            mPageCount = count;
            return this;
        }

        @JsonProperty("perpage")
        public Builder setPerPage(int perPage) {
            mPerPage = perPage;
            return this;
        }

        public Builder setTotal(int total) {
            mTotal = total;
            return this;
        }

        @JsonProperty("photo")
        public Builder setPhotos(List<Photo> photos) {
            mPhotos = photos;
            return this;
        }

        public Photos build() {
            return new Photos(this);
        }
    }
}
