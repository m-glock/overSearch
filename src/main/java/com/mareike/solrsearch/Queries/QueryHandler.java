package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ContentTypes;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class QueryHandler {

    private HashMap<Filter, String> filters;
    private SolrQuery query;

    public QueryHandler(){
        query = new SolrQuery();
        filters = new HashMap<>();
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
        String s;
        for (Filter f : filters.keySet()){
            switch(f.type){
                case "filter":
                    s = builtQueryParameter(f);
                    System.out.println("query will be: " + f.value + ":" + s);
                    query.addFilterQuery(f.value + ":" + s);
                    break;
                case "boost":
                    s = builtQueryParameter(f);
                    System.out.println("query will be: " + f.value + ":" + s);
                    query.add("bq", f.value + ":" + s);
                    break;
                case "sort":
                    if (filters.get(f).equals("creation date")){
                        query.setSort("meta_creation_date", SolrQuery.ORDER.desc);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private String builtQueryParameter(Filter f){
        String s = "\"" + filters.get(f) + "\"";
        if(f.value.equals("content_type")){
            s = "\"" + ContentTypes.getSolrValues(filters.get(f)) + "\"";
        }else if(f.value.equals("meta_creation_date")){
            String timespan = filters.get(f);
            if(timespan.equals("24 hours")){
                s = "[NOW-1DAY TO NOW]";
            }else if(timespan.equals("last week")){
                s = "[NOW-7DAY TO NOW]";
            }else if(timespan.equals("last month")){
                s = "[NOW-1MONTH TO NOW]";
            }else if(timespan.equals("last year")){
                s = "[NOW-1YEAR TO NOW]";
            }else{
                s = "[]";
            }
        }
        return s;
    }
}
