package com.t28.rxweather.rx;

import com.t28.rxweather.data.model.Coordinate;

public class CoordinateEventBus extends EventBus<Coordinate> {
    private CoordinateEventBus() {
        super();
    }

    public static class Retriever {
        private static final CoordinateEventBus BUS = new CoordinateEventBus();

        public static CoordinateEventBus retrieve() {
            return BUS;
        }

        private Retriever() {
        }
    }
}
