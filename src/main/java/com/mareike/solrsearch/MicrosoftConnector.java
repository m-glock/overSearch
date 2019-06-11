package com.mareike.solrsearch;

import com.mareike.solrsearch.msauth.ClientCredentialProvider;
import com.mareike.solrsearch.msauth.Constants;
import com.mareike.solrsearch.msauth.NationalCloud;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IListCollectionPage;
import com.microsoft.graph.requests.extensions.IUserCollectionPage;

import java.util.ArrayList;
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

    public List<String> getAllFiles(){
        ArrayList<String> urlList = new ArrayList<>();
        List<Group> groups = graphClient.groups().buildRequest().get().getCurrentPage();

        for(Group group : groups){
            String id = group.id;
            List<Drive> drives = graphClient.groups(id).drives().buildRequest().get().getCurrentPage();
            for(Drive drive : drives){
                String driveID = drive.id;
                List<DriveItem> driveItems = graphClient.groups(id).drives(driveID).root().children().buildRequest().get().getCurrentPage();
                for(DriveItem item : driveItems){
                    urlList.add(item.webUrl);
                }
            }
        }

        /*List<Site> sites = graphClient.sites().buildRequest().get().getCurrentPage();
        for(Site site : sites) {
            String id = site.id;
            List<com.microsoft.graph.models.extensions.List> lists = graphClient.sites(id).lists().buildRequest().get().getCurrentPage();
            for(com.microsoft.graph.models.extensions.List list : lists){
                List<ListItem> item = list.items.getCurrentPage();

            }
        }*/

        return urlList;
    }
}
