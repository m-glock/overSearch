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
            //String path = fieldValue(doc, "path").replaceAll("\\\\","/");
            String path = fieldValue(doc, "path");
            File file = new File(path);
            //TODO: exception handling
            try {
                URL url = file.toURI().toURL();
                document += "<h2><a href=\"" + url + "\">" + fieldValue(doc, "stream_name") + "</a></h2>";
            }catch(MalformedURLException ex){
                document += "<h2>" + fieldValue(doc, "stream_name") + "</a></h2>";
            }
            document += fieldValue(doc, "content_type");
            document += fieldValue(doc, "meta_creation_date");
            document += fieldValue(doc, "creator");
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
