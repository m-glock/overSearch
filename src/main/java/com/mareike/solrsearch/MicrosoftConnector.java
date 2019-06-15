package com.mareike.solrsearch;

import com.mareike.solrsearch.MSAuth.ClientCredentialProvider;
import com.mareike.solrsearch.MSAuth.Constants;
import com.mareike.solrsearch.MSAuth.NationalCloud;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import org.apache.commons.io.IOUtils;

import java.io.*;
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
                InputStream content = graphClient.groups(id).sites("root").lists("Documents").items(item.id).driveItem().content().buildRequest().get();
                StringWriter writer = new StringWriter();
                String encoding = "UTF8";
                try {
                    IOUtils.copy(content, writer, encoding);
                    System.out.println(writer.toString());
                } catch(IOException io) {
                    System.out.println("IOException");
                }
            }
        }

        System.out.println("end of retrieving sp files");
        return urlList;
    }
}
