package com.mareike.solrsearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;

public class Authenticator {
    //Get a valid access token using the client credential flow
    private String grantType = "client_credentials";
    private String tokenEndpoint = "https://login.microsoftonline.com/" + Constants.tenant + "/oauth2/v2.0/token";
    private String authorizationEndpoint = "https://login.microsoftonline.com/" + Constants.tenant + "/oauth2/v2.0/authorize";
    private String scope = "https%3A%2F%2Fgraph.microsoft.com%2F.default";
    private String accessToken = null;

    protected IAuthenticationProvider authenticationProvider = null;

    public Authenticator() { }

    public IAuthenticationProvider getAuthenticationProvider()
    {
        if (authenticationProvider == null) {
            try {
                accessToken = getAccessToken().replace("\"", ""); //strips out excess quotes
                //System.out.println("Access Token: " + accessToken);
                authenticationProvider = new IAuthenticationProvider() {
                    @Override
                    public void authenticateRequest(final IHttpRequest request) {
                        request.addHeader("Authorization",
                                "Bearer " + accessToken);
                    }
                };

            }
            catch (Exception e)
            {
                throw new Error("Could not create authentication provider: " + e.getLocalizedMessage());
            }
        }
        return authenticationProvider;
    }

    /**
     * Manual POST request to get an access token with the client credential flow
     * @return a string access token
     */
    private String getAccessToken()
    {
        try {
            //starting directly with token endpoint, need code from authorization endpoint before that?
            URL url = new URL(tokenEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String line;
            StringBuilder jsonString = new StringBuilder();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            String secret = Constants.clientSecret.replace("+", "%2B").replace("=", "%3D").replace("/", "%2F");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            String payload = String.format("grant_type=%1$s&scope=%2$s&client_id=%3$s&client_secret=%4$s",
                    grantType,
                    scope,
                    Constants.clientId,
                    secret);
            writer.write(payload);
            writer.close();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line = br.readLine()) != null){
                    jsonString.append(line);
                }
                br.close();
            } catch (Exception e) {
                throw new Error("Error reading authorization response: " + e.getLocalizedMessage());
            }
            conn.disconnect();

            JsonObject res = new GsonBuilder().create().fromJson(jsonString.toString(), JsonObject.class);
            System.out.println("JSON;" + res.toString());
            return res.get("access_token").toString().replaceAll("\"", "");

        } catch (Exception e) {
            throw new Error("Error retrieving access token: " + e.getLocalizedMessage());
        }
    }

    public String getAccessToken2(){
        try{
            URL url = new URL(authorizationEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String line;
            StringBuilder jsonString = new StringBuilder();
            conn.setRequestMethod("GET");




        }catch(Exception e){

        }

        return "";
    }

    public String accessToken(){
        return accessToken;
    }
}