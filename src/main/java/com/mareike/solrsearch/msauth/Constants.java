package com.mareike.solrsearch.MSAuth;

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

    //solrSearch imibachelor
    public final static String CLIENTID = "65ce0e28-4e0d-4e19-b1b3-d81b34cc5f9c";
    public final static String CLIENTSECRET = "lhe_c-?532EV1f/k6Wnb08aTn-OgvSh6";
    public final static String TENANT = "e4a09b27-9de1-4dba-a0bf-84c861f3fe2e";

    public final static String[] SCOPES = {"https://graph.microsoft.com/.default"};
}