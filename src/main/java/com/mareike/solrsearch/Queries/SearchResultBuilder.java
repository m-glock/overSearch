package com.mareike.solrsearch.Queries;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.HashMap;
import java.util.Map;

public class SearchResultBuilder {


    public static String getHTMLForResults(SolrDocumentList list){
        String html = "";
        for (SolrDocument doc : list){
            String document = "div>";
            //TODO: add picture for content type
            document += "<h4>" + fieldValue(doc, "stream_name") + "</h4>";
            document += fieldValue(doc, "content_type");
            document += fieldValue(doc, "meta_creation_date");
            document += fieldValue(doc, "owner");
            document += fieldValue(doc, "path");

            document += "</div>";
            html += document;
        }
        return html;
    }

    private static String fieldValue(SolrDocument doc, String fieldName){
        //if the fields exists return the value, otherwise return "unknown"
        return (doc.getFieldValue(fieldName) == null) ? "unknown" : doc.getFieldValue(fieldName).toString().replace("[]","");
    }
}
