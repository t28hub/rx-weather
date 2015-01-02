package com.t28.rxweather;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class WeatherRequest extends Request<Weather> {
    private WeatherRequest(Builder builder) {
        super(Method.GET, buildUrl(builder), null);
    }

    @Override
    protected Response<Weather> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Weather response) {

    }

    private static String buildUrl(Builder builder) {
        final Uri.Builder urlBuilder = new Uri.Builder();
        urlBuilder.scheme("http").authority("api.openweathermap.org").path("/data/2.5/weather");
        urlBuilder.appendQueryParameter("APPID", builder.mApiKey);

        if (!TextUtils.isEmpty(builder.mCityName) && !TextUtils.isEmpty(builder.mCountryCode)) {
            urlBuilder.appendQueryParameter("q", builder.mCityName + "," + builder.mCountryCode);
        }

        if (!TextUtils.isEmpty(builder.mCityId)) {
            urlBuilder.appendQueryParameter("id", builder.mCityId);
        }

        if (!Double.isNaN(builder.mLat)) {
            urlBuilder.appendQueryParameter("lat", String.valueOf(builder.mLat));
        }
        if (!Double.isNaN(builder.mLon)) {
            urlBuilder.appendQueryParameter("lon", String.valueOf(builder.mLon));
        }
        return urlBuilder.build().toString();
    }

    public static class Builder {
        private static final double NO_LAT = Double.NaN;
        private static final double NO_LON = Double.NaN;

        private final String mApiKey;

        private String mCityName;
        private String mCountryCode;
        private String mCityId;
        private double mLat = NO_LAT;
        private double mLon = NO_LON;

        public Builder(String apiKey) {
            mApiKey = apiKey;
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
