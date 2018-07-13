package com.jornco.mframework.libs.utils;

/**
 * Created by kkopite on 2018/7/13.
 */

public class Secrets {

    private Secrets() {

    }

    public static final class Api {
        public final class Endpoint {
            public static final String PRODUCTION = "https://dev.robospace.cc/api/";
            public static final String STAGING = "https://dev.robospace.cc/api/";
        }

        public static final class Client {
            public static final String LOCAL = "local";
            public static final String PRODUCTION = "production";
            public static final String STAGING = "staging";
        }

    }
}
