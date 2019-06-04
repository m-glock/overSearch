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

    //solrSearchEnterprise imiba - working
    /*public final static String CLIENTID = "f790fe06-5b01-439a-af4b-3d1c8c6ea2f1";
    public final static String CLIENTSECRET = "I_UEMKyPlm:.7/CMUM81kxteTSCcurC3";
    public final static String TENANT = "f535a890-cd9a-4331-88f6-3c188ce19127";*/

    //solrSearch imiba AAD - working
    public final static String CLIENTID = "4e4d4263-af02-4741-bd67-ac0e3befccf1";
    public final static String CLIENTSECRET = "O.GIGj9uk/FLinMNgGh8+aJnrDAP7T+8";
    public final static String TENANT = "f535a890-cd9a-4331-88f6-3c188ce19127";


    public final static String REDIRECTURI = "overSearch://auth";
    public final static String[] SCOPES = {"https://graph.microsoft.com/.default"};
}