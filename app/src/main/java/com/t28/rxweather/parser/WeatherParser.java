package com.t28.rxweather.parser;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t28.rxweather.model.Weather;

import java.io.IOException;

public class WeatherParser implements Parser<Weather> {
    private final ObjectMapper mMapper;

    public WeatherParser() {
        this(new ObjectMapper());
    }

    WeatherParser(@NonNull ObjectMapper mapper) {
        mMapper = mapper;
    }

    public Weather parse(byte[] body) throws ParseException {
        if (body == null || body.length == 0) {
            throw new ParseException("'body' is empty");
        }

        try {
            return mMapper.readValue(body, Weather.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
