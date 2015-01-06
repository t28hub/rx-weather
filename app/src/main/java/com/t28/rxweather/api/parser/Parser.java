package com.t28.rxweather.api.parser;

import com.t28.rxweather.data.model.Model;

public interface Parser<T extends Model> {
    T parse(byte[] data) throws ParseException;
}
