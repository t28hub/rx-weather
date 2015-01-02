package com.t28.rxweather.request;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.t28.rxweather.model.Weather;
import com.t28.rxweather.parser.JsonParser;
import com.t28.rxweather.parser.ParseException;
import com.t28.rxweather.volley.ListenableRequest;

import org.apache.http.HttpStatus;

public class WeatherRequest extends ListenableRequest<Weather> {
    private WeatherRequest(Builder builder) {
        super(Method.GET, buildUrl(builder));
    }

    @Override
    protected Response<Weather> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != HttpStatus.SC_OK) {
            return Response.error(new VolleyError("Invalid status code:" + response.statusCode));
        }

        if (response.data == null || response.data.length == 0) {
            return Response.error(new VolleyError("Empty response body"));
        }

        final Weather weather;
        try {
            weather = new JsonParser().parse(response.data, Weather.class);
        } catch (ParseException e) {
            return Response.error(new VolleyError(e));
        }

        if (weather == null) {
            return Response.error(new VolleyError("Parsed result is empty"));
        }
        if (!weather.isValid()) {
            return Response.error(new VolleyError("Parsed result is invalid:" + weather));
        }
        return Response.success(weather, null);
    }

    private static String buildUrl(Builder builder) {
        final Uri.Builder urlBuilder = new Uri.Builder();
        urlBuilder.scheme("http").authority("api.openweathermap.org").path("/data/2.5/weather");
        urlBuilder.appendQueryParameter("APPID", builder.mApiKey);

        if (!TextUtils.isEmpty(builder.mCityName) && !TextUtils.isEmpty(builder.mCountryCode)) {
            urlBuilder.appendQueryParameter("q", builder.mCityName + "," + builder.mCountryCode);
        }

        if (builder.mCityId != Weather.NO_CITY_ID) {
            urlBuilder.appendQueryParameter("id", String.valueOf(builder.mCityId));
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
        private int mCityId = Weather.NO_CITY_ID;
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

        public Builder setCityId(int id) {
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
