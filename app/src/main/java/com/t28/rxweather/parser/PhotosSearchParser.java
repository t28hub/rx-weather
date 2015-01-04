package com.t28.rxweather.parser;

import com.t28.rxweather.model.Photos;

import java.io.IOException;

public class PhotosSearchParser extends JacksonParser<Photos> {
    @Override
    public Photos parse(byte[] body) throws ParseException {
        try {
            return getMapper().readValue(body, Photos.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
