package com.t28.rxweather.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Model {

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getCanonicalName());

        final ObjectMapper mapper = new ObjectMapper();
        try {
            builder.append(mapper.writeValueAsString(this));
            return builder.toString();
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }

    public abstract boolean isValid();
}
