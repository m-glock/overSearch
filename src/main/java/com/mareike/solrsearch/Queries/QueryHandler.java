package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ContentTypes;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.io.IOException;
import java.util.HashMap;

public class QueryHandler {

    private HashMap<Filter, String> filters;
    private SolrQuery query;

    public QueryHandler(){
        query = new SolrQuery();
        //generateQueryParameters();
    }

    public void setFilters(HashMap<Filter, String> filters){ this.filters = filters; }

    public QueryResponse sendQuery(HttpSolrClient client, String queryWords){
        //send query to Solr server
        generateQueryParameters();
        addFilterAndPreferences();
        query.setQuery(queryWords);
        System.out.println("parameter are: " + query.toString());

        QueryResponse response;
        try{
            response = client.query(query);
            return response;
        }catch(IOException io){
            System.out.println("IOException");
        }catch(SolrServerException ser){
            System.out.println("SolrServerException");
        }catch(Exception e){
            System.out.println("Unknown Exception. " + e.getMessage());
        }finally {
            query = new SolrQuery();
        }
        return null;
    }

    private void generateQueryParameters(){
        //these parameters are the same for every query
        query.set("defType", "edismax");
        query.setRows(20);
        query.setHighlight(true);
        query.setHighlightFragsize(500);
        query.addHighlightField("_text_");
    }

    private void addFilterAndPreferences(){
        if(filters.isEmpty()){
            System.out.println("no filters");
        }
        for (Filter f : filters.keySet()){
            switch(f.type){
                case "filter":
                    System.out.println("parameter: " + f.value + ":" + filters.get(f));
                    String s = filters.get(f);
                    if(f.value.equals("content_type")){
                        s = ContentTypes.getSolrValues(s);
                    }
                    query.addFilterQuery(f.value + ":" + "\"" + s + "\"");
                    break;
                case "boost":
                    /*query.add
                    addParameter(, f.value + ":[" + filters.get(f) + "]");*/
                    break;
                case "sort":
                    //query.setSort(f.value, SolrQuery.ORDER.asc);
                    break;
                default:
                    break;
            }
        }
    }
}
