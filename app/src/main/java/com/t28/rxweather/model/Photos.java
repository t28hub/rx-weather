package com.t28.rxweather.model;

public class Photos extends Model {
    private final int mPage;
    private final int mPageCount;
    private final int mPerPage;
    private final int mTotal;

    private Photos(Builder builder) {
        mPage = builder.mPage;
        mPageCount = builder.mPageCount;
        mPerPage = builder.mPerPage;
        mTotal = builder.mTotal;
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

    public static class Builder {
        private int mPage;
        private int mPageCount;
        private int mPerPage;
        private int mTotal;

        public Builder() {
        }

        public Builder setPage(int page) {
            mPage = page;
            return this;
        }

        public Builder setPageCount(int count) {
            mPageCount = count;
            return this;
        }

        public Builder setPerPage(int perPage) {
            mPerPage = perPage;
            return this;
        }

        public Builder setTotal(int total) {
            mTotal = total;
            return this;
        }

        public Photos build() {
            return new Photos(this);
        }
    }
}
