package com.t28.rxweather.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.t28.rxweather.model.Photos;
import com.t28.rxweather.volley.ListenableRequest;

public class PhotosSearchRequest extends ListenableRequest<Photos> {
    public PhotosSearchRequest(int method, String url) {
        super(method, url);
    }

    @Override
    protected Response<Photos> parseNetworkResponse(NetworkResponse response) {
        return null;
    }
}
