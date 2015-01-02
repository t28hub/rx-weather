package com.t28.rxweather;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class WeatherRequest extends Request<Weather> {
    private WeatherRequest(Builder builder) {
        super(Method.GET, null, null);
    }

    @Override
    protected Response<Weather> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Weather response) {

    }

    public static class Builder {
        private static final double NO_LAT = Double.NaN;
        private static final double NO_LON = Double.NaN;

        private String mCityName;
        private String mCountryCode;
        private String mCityId;
        private double mLat = NO_LAT;
        private double mLon = NO_LON;

        public Builder() {
        }

        public Builder setCityName(String name) {
            mCityName = name;
            return this;
        }

        public Builder setCountryCode(String code) {
            mCountryCode = code;
            return this;
        }

        public Builder setCityId(String id) {
            mCityId = id;
            return this;
        }

        public Builder setLat(double lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(double lon) {
            mLon = lon;
            return this;
        }

        public WeatherRequest build() {
            return new WeatherRequest(this);
        }
    }
}
