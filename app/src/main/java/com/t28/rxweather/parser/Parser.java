package com.t28.rxweather.parser;

import android.support.annotation.NonNull;

import com.t28.rxweather.Validatable;

public interface Parser<T extends Validatable> {
    T parse(@NonNull byte[] body) throws ParseException;

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(Throwable cause) {
            super(cause);
        }
    }
}
