package com.t28.rxweather.api.request;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.t28.rxweather.data.model.City;
import com.t28.rxweather.data.model.Forecast;
import com.t28.rxweather.api.parser.ForecastParser;
import com.t28.rxweather.api.parser.ParseException;
import com.t28.rxweather.volley.ListenableRequest;

import org.apache.http.HttpStatus;

public class ForecastRequest extends ListenableRequest<Forecast> {
    private ForecastRequest(Builder builder) {
        super(Method.GET, buildUrl(builder));
    }

    @Override
    protected Response<Forecast> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != HttpStatus.SC_OK) {
            return Response.error(new VolleyError("Invalid status code:" + response.statusCode));
        }

        final Forecast forecast;
        try {
            forecast = new ForecastParser().parse(response.data);
        } catch (ParseException e) {
            return Response.error(new VolleyError(e));
        }

        return Response.success(forecast, null);
    }

    private static String buildUrl(Builder builder) {
        final Uri.Builder urlBuilder = new Uri.Builder();
        urlBuilder.scheme("http").authority("api.openweathermap.org").path("/data/2.5/forecast");
        urlBuilder.appendQueryParameter("APPID", builder.mApiKey);

        if (!TextUtils.isEmpty(builder.mCityName)){
            if (TextUtils.isEmpty(builder.mCountryCode)) {
                urlBuilder.appendQueryParameter("q", builder.mCityName);
            } else {
                urlBuilder.appendQueryParameter("q", builder.mCityName + "," + builder.mCountryCode);
            }
        }

        if (builder.mCityId != City.NO_ID) {
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
        private int mCityId = City.NO_ID;
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

        public ForecastRequest build() {
            return new ForecastRequest(this);
        }
    }
}
