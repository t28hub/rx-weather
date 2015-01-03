package com.t28.rxweather.parser;

import com.t28.rxweather.model.Weather;

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