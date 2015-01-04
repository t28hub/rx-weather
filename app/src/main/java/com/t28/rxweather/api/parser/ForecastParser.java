package com.t28.rxweather.api.parser;

import com.t28.rxweather.data.model.Forecast;

import java.io.IOException;

public class ForecastParser extends JacksonParser<Forecast> {
    @Override
    public Forecast parse(byte[] body) throws ParseException {
        try {
            return getMapper().readValue(body, Forecast.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
