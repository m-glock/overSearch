package com.mareike.solrsearch;

import com.mareike.solrsearch.msauth.ClientCredentialProvider;
import com.mareike.solrsearch.msauth.Constants;
import com.mareike.solrsearch.msauth.NationalCloud;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserCollectionPage;

import java.util.Arrays;
import java.util.List;

public class MicrosoftConnector {

    private IGraphServiceClient graphClient;


    public MicrosoftConnector(){
        //authenticate with MS Graph
        ClientCredentialProvider authProvider = authenticate();
        graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
    }


    public ClientCredentialProvider authenticate(){
        return new ClientCredentialProvider(
                Constants.CLIENTID,
                Arrays.asList(Constants.SCOPES),
                Constants.CLIENTSECRET,
                Constants.TENANT,
                NationalCloud.Global
        );
    }

    public List<User> getUsers(){
        //get users
        List<User> users;
        IUserCollectionPage page = graphClient.users().buildRequest().get();
        users = page.getCurrentPage();
        return users;
    }
}
