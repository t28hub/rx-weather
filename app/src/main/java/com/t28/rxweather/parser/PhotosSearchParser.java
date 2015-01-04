package com.t28.rxweather.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.t28.rxweather.data.model.Photos;

import java.io.IOException;

public class PhotosSearchParser extends JacksonParser<Photos> {
    @Override
    public Photos parse(byte[] body) throws ParseException {
        try {
            // UNWRAP_ROOT_VALUEを指定するとstatフィールドでパース失敗するため一時的に別のオブジェクトにする
            final PhotosHolder holder = getMapper().readValue(body, PhotosHolder.class);
            return holder.photos;
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PhotosHolder {
        public Photos photos;
    }
}
