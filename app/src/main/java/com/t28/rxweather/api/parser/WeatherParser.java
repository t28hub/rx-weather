package com.t28.rxweather.api.parser;

import com.t28.rxweather.data.model.Weather;

import java.io.IOException;

public class WeatherParser extends JacksonParser<Weather> {

    @Override
    public Weather parse(byte[] body) throws ParseException {
        try {
            return getMapper().readValue(body, Weather.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
