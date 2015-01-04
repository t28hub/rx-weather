package com.t28.rxweather.data.model;

public enum PhotoSize {
    SMALL {
        private static final String SUFFIX = "m";

        @Override
        public String toSuffix() {
            return SUFFIX;
        }
    },
    MEDIUM {
        private static final String SUFFIX = "z";

        @Override
        public String toSuffix() {
            return SUFFIX;
        }
    },
    LARGE {
        private static final String SUFFIX = "b";

        @Override
        public String toSuffix() {
            return SUFFIX;
        }
    },
    THUMBNAIL {
        private static final String SUFFIX = "t";

        @Override
        public String toSuffix() {
            return SUFFIX;
        }
    };

    public String toSuffix() {
        throw new AbstractMethodError();
    }
}
