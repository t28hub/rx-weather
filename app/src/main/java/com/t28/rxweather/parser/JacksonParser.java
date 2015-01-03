package com.t28.rxweather.parser;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t28.rxweather.model.Model;

public abstract class JacksonParser<T extends Model> implements Parser<T> {
    private final ObjectMapper mMapper;

    public JacksonParser() {
        this(new ObjectMapper());
    }

    public JacksonParser(@NonNull ObjectMapper mapper) {
        mMapper = mapper;
    }

    protected ObjectMapper getMapper() {
        return mMapper;
    }
}
