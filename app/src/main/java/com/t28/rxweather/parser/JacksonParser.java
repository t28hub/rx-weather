package com.t28.rxweather.parser;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.t28.rxweather.model.Validatable;

import java.io.IOException;

public abstract class JacksonParser<T extends Validatable> implements Parser<T> {
    private final JsonFactory mFactory;

    public JacksonParser() {
        this(new JsonFactory());
    }

    public JacksonParser(@NonNull JsonFactory factory) {
        mFactory = factory;
    }

    protected JsonParser newParser(byte[] data) throws ParseException {
        try {
            return mFactory.createParser(data);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
