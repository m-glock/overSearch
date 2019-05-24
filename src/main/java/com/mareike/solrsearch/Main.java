package com.mareike.solrsearch;

import java.io.*;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;


public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {
        /*String urlString = "http://localhost:8983/solr/localDocs21";
        HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

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

        String urlString = "http://localhost:8983/solr/test9";
        HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
        //CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder(urlString).build();

        //is working
        /*for(int i=0;i<1000;++i) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("cat", "book");
            doc.addField("id", "book-" + i);
            doc.addField("name", "The Legend of the Hobbit part " + i);
            solr.add(doc);
            if(i%100==0) solr.commit();  // periodically flush
        }*/

        //is not working
        File file = new File("C:\\Users\\mareike\\Desktop\\Bachelorarbeit.pdf");

        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");

        req.addFile(file, "application/pdf");

        //req.setParam("literal.id", "doc1");
        //req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);


        try{
            solr.request(req);
        }
        catch(IOException e){
            PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
            e.printStackTrace(out);
            out.print("IOException message: ");
            out.close();
            System.out.println("IO message: " + e.getMessage());
        } catch(SolrServerException e){
            PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
            out.print("SolrServer message:");
            e.printStackTrace(out);
            out.close();
            System.out.println("SolrServer message: " + e.getMessage());
        } catch(HttpSolrClient.RemoteSolrException e){
            PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
            out.print("RemoteSolrException message: ");
            e.printStackTrace(out);
            out.close();
            System.out.println("RemoteSolrException message: " + e.getMessage());
        }catch(Exception e){
            PrintWriter out = new PrintWriter("C:\\Users\\mareike\\Desktop\\filename.txt");
            out.print("UnknownException message: ");
            e.printStackTrace(out);
            out.close();
            System.out.println("UnknownException message: " + e.getMessage());
            System.out.println("exception" + e.getClass().getName());
        }finally{
            solr.commit();
        }

    }
}