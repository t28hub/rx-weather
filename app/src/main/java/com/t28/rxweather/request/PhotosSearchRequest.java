package com.t28.rxweather.request;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.t28.rxweather.data.model.Photos;
import com.t28.rxweather.parser.ParseException;
import com.t28.rxweather.parser.PhotosSearchParser;
import com.t28.rxweather.util.BooleanUtils;
import com.t28.rxweather.util.CollectionUtils;
import com.t28.rxweather.volley.ListenableRequest;

import org.apache.http.HttpStatus;

import java.util.List;

/**
 * {@see https://www.flickr.com/services/api/flickr.photos.search.html}
 */
public class PhotosSearchRequest extends ListenableRequest<Photos> {
    private static final String KEY_METHOD = "method";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_TAG_MODE = "tag_mode";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";
    private static final String KEY_RADIUS = "radius";
    private static final String KEY_RADIUS_UNITS = "radius_units";
    private static final String KEY_PAGE = "page";
    private static final String KEY_PER_PAGE = "per_page";
    private static final String KEY_FORMAT = "format";
    private static final String KEY_NO_JSON_CALLBACK = "nojsoncallback";

    private static final String METHOD = "flickr.photos.search";

    private static final String TAG_DELIMITER = ",";

    public PhotosSearchRequest(Builder builder) {
        super(Method.GET, buildUrl(builder));
    }

    @Override
    protected Response<Photos> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != HttpStatus.SC_OK) {
            return Response.error(new VolleyError("Invalid status code:" + response.statusCode));
        }

        final Photos photos;
        try {
            photos = new PhotosSearchParser().parse(response.data);
        } catch (ParseException e) {
            return Response.error(new VolleyError(e));
        }

        if (photos == null || !photos.isValid()) {
            return Response.error(new VolleyError("Parsed result is not valid"));
        }
        return Response.success(photos, null);
    }

    private static String buildUrl(Builder builder) {
        final Uri.Builder urlBuilder = Uri.parse("https://api.flickr.com/services/rest/").buildUpon();
        urlBuilder.appendQueryParameter(KEY_METHOD, METHOD);
        urlBuilder.appendQueryParameter(KEY_API_KEY, builder.mApiKey);

        if (!CollectionUtils.isEmpty(builder.mTags)) {
            final String tags = TextUtils.join(TAG_DELIMITER, builder.mTags);
            urlBuilder.appendQueryParameter(KEY_TAGS, tags);
        }

        if (!TextUtils.isEmpty(builder.mTagMode)) {
            urlBuilder.appendQueryParameter(KEY_TAG_MODE, builder.mTagMode);
        }

        if (!Float.isNaN(builder.mLat) && !Float.isNaN(builder.mLon)) {
            final String lat = String.valueOf(builder.mLat);
            urlBuilder.appendQueryParameter(KEY_LAT, lat);

            final String lon = String.valueOf(builder.mLon);
            urlBuilder.appendQueryParameter(KEY_LON, lon);
        }

        if (builder.mRadius != Builder.NO_RADIUS) {
            final String radius = String.valueOf(builder.mRadius);
            urlBuilder.appendQueryParameter(KEY_RADIUS, radius);
        }

        if (!TextUtils.isEmpty(builder.mRadiusUnits)) {
            urlBuilder.appendQueryParameter(KEY_RADIUS_UNITS, builder.mRadiusUnits);
        }

        urlBuilder.appendQueryParameter(KEY_PAGE, String.valueOf(builder.mPage));
        urlBuilder.appendQueryParameter(KEY_PER_PAGE, String.valueOf(builder.mPerPage));

        if (!TextUtils.isEmpty(builder.mFormat)) {
            urlBuilder.appendQueryParameter(KEY_FORMAT, builder.mFormat);
        }

        final String noJsonCallback = String.valueOf(BooleanUtils.toInt(builder.mNoJsonCallback));
        urlBuilder.appendQueryParameter(KEY_NO_JSON_CALLBACK, noJsonCallback);

        return urlBuilder.build().toString();
    }

    public static class Builder {
        public static final String TAG_MODE_ANY = "any";
        public static final String TAG_MODE_ALL = "all";
        public static final String RADIUS_UNITS_ML = "ml";
        public static final String RADIUS_UNITS_KM = "km";
        public static final String FORMAT_JSON = "json";

        public static final float NO_LAT = Float.NaN;
        public static final float NO_LON = Float.NaN;
        public static final int NO_RADIUS = -1;
        public static final int DEFAULT_PAGE = 1;
        public static final int DEFAULT_PER_PAGE = 25;

        private final String mApiKey;

        private List<String> mTags;
        private String mTagMode;

        private float mLat = NO_LAT;
        private float mLon = NO_LON;

        private int mRadius = NO_RADIUS;
        private String mRadiusUnits;

        private int mPage = DEFAULT_PAGE;
        private int mPerPage = DEFAULT_PER_PAGE;

        private String mFormat;
        private boolean mNoJsonCallback;

        public Builder(String apiKey) {
            mApiKey = apiKey;
        }

        public Builder setTags(List<String> tags) {
            mTags = tags;
            return this;
        }

        public Builder setTagMode(String tagMode) {
            mTagMode = tagMode;
            return this;
        }

        public Builder setLat(float lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(float lon) {
            mLon = lon;
            return this;
        }

        public Builder setRadius(int radius) {
            mRadius = radius;
            return this;
        }

        public Builder setRadiusUnit(String units) {
            mRadiusUnits = units;
            return this;
        }

        public Builder setPage(int page) {
            mPage = page;
            return this;
        }

        public Builder setPerPage(int perPage) {
            mPerPage = perPage;
            return this;
        }

        public Builder setFormat(String format) {
            mFormat = format;
            return this;
        }

        public Builder setNoJsonCallback(boolean enable) {
            mNoJsonCallback = enable;
            return this;
        }

        public PhotosSearchRequest build() {
            return new PhotosSearchRequest(this);
        }
    }
}
