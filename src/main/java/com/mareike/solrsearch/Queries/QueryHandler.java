package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ParameterType;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryHandler {

    private HashMap<String, String> parameters;
    private SolrDocumentList docs;

    public QueryHandler(){
        parameters = new HashMap<>();
        docs = new SolrDocumentList();
    }

    public void addParameter(ParameterType type, String values){
        parameters.put(type.parameter, values);
    }

    public SolrDocumentList sendQuery(HttpSolrClient client){
        //send query to Solr server
        SolrParams params = generateQueryParameters();
        System.out.println("parameter are: " + params);
        try{
            QueryResponse response = client.query(params);
            List<String> content = response.getHighlighting().get("778c5b0e-91cd-4140-b509-64d6e62d1d4f").get("_text_");
            for(String st : content){
                System.out.println("highlight is: " + st);
            }
            //System.out.println("response is: " + response);
            docs = response.getResults();
        }catch(IOException io){
            System.out.println("IOException");
        }catch(SolrServerException ser){
            System.out.println("SolrServerException");
        }catch(Exception e){
            System.out.println("Unknown Exception");
        }
        parameters.clear();
        System.out.println("map is empty: " + parameters.isEmpty());
        return docs;
    }

    private MapSolrParams generateQueryParameters(){
        //TODO: get parameters from UI and call addParameter()

        //these parameters are the same for every query
        parameters.put("defType", "edismax");
        parameters.put("timeAllowed", "10000");
        parameters.put("hl", "true");
        parameters.put("hl.snippets", "100");
        parameters.put("hl.fl", "_text_");

        return new MapSolrParams(parameters);
    }
}
