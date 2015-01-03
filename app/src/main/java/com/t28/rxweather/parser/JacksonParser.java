package com.t28.rxweather.parser;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.*;

import java.io.IOException;

public abstract class JacksonParser implements Parser {
    private final JsonFactory mFactory;

    public JacksonParser() {
        this(new JsonFactory());
    }

    public JacksonParser(@NonNull JsonFactory factory) {
        mFactory = factory;
    }

    protected JsonParser newParser(byte[] data) throws IOException {
        return mFactory.createParser(data);
    }
}
