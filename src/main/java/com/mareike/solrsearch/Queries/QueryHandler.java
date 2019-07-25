package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ContentTypes;
import com.mareike.solrsearch.Main;
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
        filters = new HashMap<>();
    }

    public void setFilters(HashMap<Filter, String> filters){ this.filters = filters; }

    public HashMap<Filter, String> getFilters(){ return filters; }

    public QueryResponse sendQuery(HttpSolrClient client, String queryWords){
        //send query to Solr server
        Main.logger.info("Sending query to Solr...");
        generateQueryParameters();
        addFilterAndPreferences();
        query.setQuery(queryWords);
        Main.logger.info("Parameters of the query are: " + query.toString());

        QueryResponse response;
        try{
            response = client.query(query);
            Main.logger.info("response to query is: " + response);
            return response;
        }catch(IOException io){
            Main.logger.info("IOException");
        }catch(SolrServerException ser){
            Main.logger.info("SolrServerException");
        }catch(Exception e){
            Main.logger.info("Unknown Exception. " + e.getMessage());
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
        String s;
        for (Filter f : filters.keySet()){
            switch(f.type){
                case "filter":
                    s = builtQueryParameter(f);
                    query.addFilterQuery(f.value + ":" + s);
                    break;
                case "boost":
                    s = builtQueryParameter(f);
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
            switch (timespan) {
                case "24 hours":
                    s = "[NOW-1DAY TO NOW]";
                    break;
                case "last week":
                    s = "[NOW-7DAY TO NOW]";
                    break;
                case "last month":
                    s = "[NOW-1MONTH TO NOW]";
                    break;
                case "last year":
                    s = "[NOW-1YEAR TO NOW]";
                    break;
                default:
                    s = "[]";
                    break;
            }
        }
        return s;
    }
}
