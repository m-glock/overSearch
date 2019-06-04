package com.mareike.solrsearch.msauth;

import java.util.List;

public class Constants {
    public static class Tenants
    {
        public static final String Common = "common";
        public static final String Organizations = "organizations";
        public static final String Consumers = "consumers";
    }
    public static final String BEARER = "Bearer ";
    public static final String TOKEN_ENDPOINT = "/oauth2/v2.0/token";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //public final static String CLIENTID = "f6bd1e4d-0ebe-453f-b601-4db109efa9de";
    public final static String CLIENTID = "7ee25081-38e5-4147-8c42-472f31cd7564";
    //public final static String CLIENTSECRET = "x6y7ZkFFlz/6YZGKCB/sLhRAptf-yH=6";
    public final static String CLIENTSECRET = "5ah5HVS6+/LHPi2*lvCgk*4-KhuvF0.G";
    public final static String TENANT = "common";
    public final static String REDIRECTURI = "overSearch://auth";
    public final static String[] SCOPES = {"User.Read"};
}