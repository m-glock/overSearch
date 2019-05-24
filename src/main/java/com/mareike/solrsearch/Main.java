package com.mareike.solrsearch;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.solr.client.solrj.SolrServerException;



public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {

        SolrInstance solr = new SolrInstance("http://localhost:8983/solr", "test10");
        IndexHandler handler = new IndexHandler(solr);
        handler.addFiles("C:\\Users\\mareike\\Desktop\\Bachelorarbeit.pdf");




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