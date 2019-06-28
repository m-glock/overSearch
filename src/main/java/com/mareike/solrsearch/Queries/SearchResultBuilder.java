package com.mareike.solrsearch.Queries;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchResultBuilder {


    public static String getHTMLForResults(SolrDocumentList list){
        String html = "";
        for (SolrDocument doc : list){
            String document = "<div>";
            //TODO: add picture for content type
            //String path = fieldValue(doc, "path").replace("\\","/");
            String path = fieldValue(doc, "path");
            //TODO: exception handling
            URL url;
            try {
                if(path.contains("sharepoint")){
                    url = new URL(path);
                }else{
                    File file = new File(path);
                    url = file.toURI().toURL();
                }
            }catch(MalformedURLException ex){
                System.out.println("URL exception");
                //TODO
                url = null;
            }
            System.out.println("Path is: " + url.toString());

            document += "<h2><a href=\"" + url + "\">" + fieldValue(doc, "stream_name") + "</a></h2>";
            //document += "<h2>" + url + " " + fieldValue(doc, "stream_name") + "</h2>";
            document += fieldValue(doc, "content_type");
            document += fieldValue(doc, "meta_creation_date");
            document += fieldValue(doc, "owner");
            document += path;

            document += "</div>";
            html += document;
        }
        return html;
    }

    private static String fieldValue(SolrDocument doc, String fieldName){
        //returns "[true]" -> remove square brackets
        String fieldValue = (doc.getFieldValue(fieldName) == null) ? "" : doc.getFieldValue(fieldName).toString();
        return fieldValue.replaceAll("\\[", "").replaceAll("]","");
    }
}
