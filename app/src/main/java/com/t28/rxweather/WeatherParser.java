package com.t28.rxweather;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t28.rxweather.model.Weather;

import java.io.IOException;

public class WeatherParser {
    private final ObjectMapper mMapper;

    public WeatherParser() {
        this(new ObjectMapper());
    }

    WeatherParser(@NonNull ObjectMapper mapper) {
        mMapper = mapper;
    }

    public Weather parse(byte[] body) throws ParseException {
        try {
            return mMapper.readValue(body, Weather.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    public static class ParseException extends VolleyError {
        public ParseException(Throwable cause) {
            super(cause);
        }
    }
}
