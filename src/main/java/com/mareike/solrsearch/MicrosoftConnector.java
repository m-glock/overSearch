package com.mareike.solrsearch;

import com.google.gson.JsonObject;
import com.mareike.solrsearch.MSAuth.ClientCredentialProvider;
import com.mareike.solrsearch.MSAuth.Constants;
import com.mareike.solrsearch.MSAuth.NationalCloud;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
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

    public List<String> getAllFiles(){
        ArrayList<String> urlList = new ArrayList<>();
        List<Group> groups = graphClient.groups().buildRequest().get().getCurrentPage();

        for(Group group : groups) {
            String id = group.id;
            List<ListItem> items = graphClient.groups(id).sites("root").lists("Documents").items().buildRequest().get().getCurrentPage();
            System.out.println("Size: " + items.size());
            for(ListItem item : items){
                urlList.add(item.webUrl);
                //TODO: get content from files

                File file = graphClient.groups(id).sites("root").lists("Documents").items(item.id).driveItem().buildRequest().get().file;
                /*InputStream content = graphClient.groups(id).sites("root").lists("Documents").items(item.id).driveItem().content().buildRequest().get();
                try {
                    int i;
                    char c;
                    System.out.println("Characters printed:");
                    while((i = content.read())!= -1) {
                        c = (char)i;
                        System.out.print(c);
                    }
                    content.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }*/
            }
        }
        System.out.println("end of retrieving sp files");
        return urlList;
    }
}
