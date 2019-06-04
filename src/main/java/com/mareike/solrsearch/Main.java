package com.mareike.solrsearch;

import java.io.*;
import java.util.Arrays;

import com.mareike.solrsearch.msauth.AuthorizationCodeProvider;
import com.mareike.solrsearch.msauth.ClientCredentialProvider;
import com.mareike.solrsearch.msauth.Constants;
import com.mareike.solrsearch.msauth.NationalCloud;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.*;
import org.apache.solr.client.solrj.SolrServerException;


public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {

        /*SolrInstance solr = new SolrInstance("http://localhost:8983/solr", "localDocs");
        IndexHandler handler = new IndexHandler(solr);
        handler.indexFiles("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2");*/

        //authenticate with MS Graph
        //TODO: how to get authorization code?
        //String authorizationCode = "";
        //AuthorizationCodeProvider authProvider = new AuthorizationCodeProvider(Constants.CLIENTID, Arrays.asList(Constants.SCOPES), authorizationCode, Constants.REDIRECTURI, NationalCloud.Germany, Constants.TENANT, Constants.CLIENTSECRET);

        ClientCredentialProvider authProvider = new ClientCredentialProvider(Constants.CLIENTID, Arrays.asList(Constants.SCOPES), Constants.CLIENTSECRET, Constants.TENANT, NationalCloud.Germany);



        final IGraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(authProvider)
                        .buildClient();

        User user = graphClient.me().buildRequest().get();

        /*URL url = new URL("https://login.microsoftonline.com/{tenant}/oauth2/v2.0/authorize?"+
                "client_id=" + Constants.clientId +
                "&response_type=code" +
                "&redirect_uri=" + Constants.redirectURI +
                "&response_mode=query" +
                "&scope=offline_access%20user.read%20mail.read" +
                "&state=12345");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());*/

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