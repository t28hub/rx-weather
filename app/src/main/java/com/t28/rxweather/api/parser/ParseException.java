package com.t28.rxweather.api.parser;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
