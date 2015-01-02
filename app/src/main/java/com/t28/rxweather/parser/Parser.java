package com.t28.rxweather.parser;

import com.t28.rxweather.Validatable;

public interface Parser<T extends Validatable> {
    T parse(byte[] body) throws ParseException;
}
