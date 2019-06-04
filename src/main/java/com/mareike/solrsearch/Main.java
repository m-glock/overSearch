package com.mareike.solrsearch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.mareike.solrsearch.msauth.*;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.*;
import org.apache.solr.client.solrj.SolrServerException;

import static com.mareike.solrsearch.msauth.Constants.*;


public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {

        /*SolrInstance solr = new SolrInstance("http://localhost:8983/solr", "localDocs");
        IndexHandler handler = new IndexHandler(solr);
        handler.indexFiles("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2");*/

        //authenticate with MS Graph
        ClientCredentialProvider authProvider = new ClientCredentialProvider(
                CLIENTID,
                Arrays.asList(SCOPES),
                CLIENTSECRET,
                TENANT,
                NationalCloud.Global
        );

        final IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();


        //get users
        graphClient.users().buildRequest().get(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                System.out.println("Success!");
                List<User> users = iUserCollectionPage.getCurrentPage();
                for(User user : users){
                    System.out.println("Found user: " + user.displayName);
                }
            }
            @Override
            public void failure(ClientException ex) {
                System.out.println("failed :(");
            }
        });


        /*HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

        System.out.println("before query");
        Query query = new Query();
        System.out.println("after query");
        //happens later from UI
        query.addParameter(ParameterType.QUERY, "singleton");
        query.addParameter(ParameterType.FIELDlIST, "id,start");

        SolrDocumentList results = query.sendQuery(solr);

        try (PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt"))  {
            if(!results.isEmpty()){
                for(SolrDocument doc : results){
                    out.println(doc.toString());
                }
            }
        }*/

    }
}