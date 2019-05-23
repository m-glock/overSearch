package com.mareike.solrsearch;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.HashMap;

public class Query {

    private HashMap<String, String> parameters;
    private SolrDocumentList docs;

    public Query(){
        parameters = new HashMap<>();
        docs = new SolrDocumentList();
    }


    private MapSolrParams generateQueryParameters(){
        //TODO: get parameters from UI and call addParameter()

        //these parameters are the same for every query
        parameters.put("defType", "edismax");
        parameters.put("timeAllowed", "10000");

        return new MapSolrParams(parameters);
    }

    public SolrDocumentList sendQuery(HttpSolrClient client){
        //send query to Solr server
        SolrParams params = generateQueryParameters();
        System.out.println("parameter are: " + params);
        try{
            QueryResponse response = client.query(params);
            System.out.println("response: " + response.toString());
            docs = response.getResults();
        }catch(IOException io){
            System.out.println("IOException");
        }catch(SolrServerException ser){
            System.out.println("SolrServerException");
        }catch(Exception e){
            System.out.println("Unknown Exception");
        }

        return docs;
    }

    public void addParameter(ParameterType type, String values){

        parameters.put(type.parameter, values);
    }
}
