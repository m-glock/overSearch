package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ParameterType;
import com.sun.research.ws.wadl.Param;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import java.io.IOException;
import java.util.HashMap;

public class QueryHandler {

    private HashMap<String, String> parameters;
    private HashMap<Filter, String> filters;

    public QueryHandler(){
        parameters = new HashMap<>();
    }

    public void addParameter(ParameterType type, String values){
        parameters.put(type.parameter, values);
    }

    public void setFilters(HashMap<Filter, String> filters){ this.filters = filters; }

    public QueryResponse sendQuery(HttpSolrClient client){
        //send query to Solr server
        SolrParams params = generateQueryParameters();
        System.out.println("parameter are: " + params);
        System.out.println("response is: ");
        QueryResponse response;
        try{
            response = client.query(params);
            return response;
        }catch(IOException io){
            System.out.println("IOException");
        }catch(SolrServerException ser){
            System.out.println("SolrServerException");
        }catch(Exception e){
            System.out.println("Unknown Exception");
        }finally {
            parameters.clear();
        }
        return null;
    }

    private MapSolrParams generateQueryParameters(){
        addFilterAndPreferences();
        //these parameters are the same for every query
        parameters.put("defType", "edismax");
        parameters.put("timeAllowed", "10000");
        parameters.put(ParameterType.ROWS.parameter,"20");
        parameters.put("hl", "true");
        parameters.put("hl.fragsize", "500");
        parameters.put("hl.fl", "_text_");
        return new MapSolrParams(parameters);
    }

    private void addFilterAndPreferences(){
        if(filters.isEmpty()){
            System.out.println("no filters");
        }
        for (Filter f : filters.keySet()){
            switch(f.type){
                case "filter":
                    addParameter(ParameterType.FILTERQUERY, "");
                    break;
                case "boost":
                    addParameter(ParameterType.BOOST, f.value + ":[" + filters.get(f) + "]");
                    break;
                case "sort":
                    addParameter(ParameterType.SORT, "");
                    break;
                default:
                    break;
            }
        }
    }
}
