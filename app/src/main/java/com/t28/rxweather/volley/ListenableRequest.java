package com.t28.rxweather.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.t28.rxweather.model.Validatable;

public abstract class ListenableRequest<T extends Validatable> extends Request<T> {
    private Response.Listener<T> mListener;
    private Response.ErrorListener mErrorListener;

    public ListenableRequest(int method, String url) {
        super(method, url, null);
    }

    public void setListener(Response.Listener<T> listener) {
        mListener = listener;
    }

    public void setErrorListener(Response.ErrorListener listener) {
        mErrorListener = listener;
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mErrorListener == null) {
            return;
        }
        mErrorListener.onErrorResponse(error);
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener == null) {
            return;
        }
        mListener.onResponse(response);
    }
}
