package com.mareike.solrsearch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.core.IClientConfig;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.CustomRequestBuilder;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDriveRequestBuilder;
import org.apache.solr.client.solrj.SolrServerException;



public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {

        /*SolrInstance solr = new SolrInstance("http://localhost:8983/solr", "localDocs");
        IndexHandler handler = new IndexHandler(solr);
        handler.indexFiles("C:\\Users\\mareike\\Documents\\Studium\\2.Semester-SS16\\Info2");*/

        //authenticate with MS Graph
        Authenticator authenticator = new Authenticator();
        IAuthenticationProvider authenticationProvider = authenticator.getAuthenticationProvider();

        StringBuilder result = new StringBuilder();
        URL url = new URL("https://graph.microsoft.com/v1.0/me/messages");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + authenticator.accessToken());
        conn.setRequestProperty("Accept","application/json");
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            System.out.println(result.toString());
        }catch(IOException io){
            System.out.println(io.getMessage());
            System.out.println(conn.getResponseMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(conn.getResponseMessage());
        }





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