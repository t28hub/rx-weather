package com.t28.rxweather.parser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    private final ObjectMapper mMapper;

    public JsonParser() {
        this(new ObjectMapper());
    }

    public JsonParser(@NonNull ObjectMapper mapper) {
        mMapper = mapper;
    }

    public <T> T parse(@Nullable byte[] body, @NonNull Class<T> valueType)
            throws Parser.ParseException {
        if (body == null || body.length == 0) {
            throw new Parser.ParseException("Empty body is an invalid JSON");
        }

        try {
            return mMapper.readValue(body, valueType);
        } catch (IOException e) {
            throw new Parser.ParseException(e);
        }
    }
}
