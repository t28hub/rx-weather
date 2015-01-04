package com.t28.rxweather.parser;

import com.t28.rxweather.data.model.Model;

public interface Parser<T extends Model> {
    T parse(byte[] body) throws ParseException;
}
