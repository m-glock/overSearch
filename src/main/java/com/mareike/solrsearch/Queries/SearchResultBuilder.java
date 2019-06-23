package com.mareike.solrsearch.Queries;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SearchResultBuilder {


    public static String getHTMLForResults(SolrDocumentList list){
        String html = "";
        for (SolrDocument doc : list){
            String document = "<p>";
            //TODO: add all display fields here - what to do if field does not exist
            document += fieldValue(doc, "stream_name");
            document += fieldValue(doc, "content_type");
            document += fieldValue(doc, "meta_creation_date");
            //doc.getFieldValue("");

            document += "</p>";
            html += document;
        }
        return html;
    }

    private static String fieldValue(SolrDocument doc, String fieldName){
        //if the fields exists return the value, otherwise return "unknown"
        return (doc.getFieldValue(fieldName) == null) ? "unknown" : doc.getFieldValue(fieldName).toString();
    }

    private class SearchResult{

        String fileName;
        String path;
        String contentType;
        String creator;
        String dateCreated;
        String dateModified;
        int pages;


        private SearchResult(){

        }
    }
}
