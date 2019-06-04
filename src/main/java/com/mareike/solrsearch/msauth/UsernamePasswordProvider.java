package com.mareike.solrsearch.msauth;

import java.util.List;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;
import com.microsoft.graph.httpcore.ICoreAuthenticationProvider;

import okhttp3.Request;

public class UsernamePasswordProvider extends BaseAuthentication implements IAuthenticationProvider, ICoreAuthenticationProvider{

    private String Username;
    private String Password;

    public UsernamePasswordProvider(
            String clientId,
            List<String> scopes,
            String username,
            String password){
        this(clientId, scopes, username, password, NationalCloud.Global, Constants.Tenants.Organizations, null);
    }

    public UsernamePasswordProvider(
            String clientId,
            List<String> scopes,
            String username,
            String password,
            NationalCloud nationalCloud,
            String tenant,
            String clientSecret) {
        super(  scopes,
                clientId,
                GetAuthority(nationalCloud == null? NationalCloud.Global: nationalCloud, tenant == null? Constants.Tenants.Organizations: tenant),
                null,
                nationalCloud == null? NationalCloud.Global: nationalCloud,
                tenant == null? Constants.Tenants.Organizations: tenant,
                clientSecret);
        this.Username = username;
        this.Password = password;
    }

    @Override
    public Request authenticateRequest(Request request) {
        String accessToken = getAccessToken();
        return request.newBuilder().addHeader("Authorization", Constants.BEARER + accessToken).build();
    }

    @Override
    public void authenticateRequest(IHttpRequest request) {
        String accessToken = getAccessToken();
        request.addHeader("Authorization", Constants.BEARER + accessToken);
    }

    String getAccessToken() {
        String accessToken = getAccessTokenSilent();
        if(accessToken == null) {
            try {
                OAuthClientRequest authRequest = getTokenRequestMessage();
                accessToken = getAccessTokenNewRequest(authRequest);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    OAuthClientRequest getTokenRequestMessage() throws OAuthSystemException {
        String tokenUrl = getAuthority() + Constants.TOKEN_ENDPOINT;
        TokenRequestBuilder token = OAuthClientRequest.
                tokenLocation(tokenUrl)
                .setClientId(getClientId())
                .setUsername(this.Username)
                .setPassword(this.Password)
                .setGrantType(GrantType.PASSWORD)
                .setScope(getScopesAsString());
        if(getClientSecret() != null) {
            token.setClientSecret(getClientSecret());
        }
        OAuthClientRequest request = token.buildBodyMessage();
        return request;
    }

    String getAccessTokenNewRequest(OAuthClientRequest request) throws OAuthSystemException, OAuthProblemException {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        setStartTime(System.currentTimeMillis());
        setResponse(oAuthClient.accessToken(request));
        return getResponse().getAccessToken();
    }
}