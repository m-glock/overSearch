package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ContentTypes;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchResultBuilder {

    public static String getHTMLForResults(QueryResponse response){
        SolrDocumentList list = response.getResults();
        if(list.isEmpty()){
            return "<div> No documents fit your query. </div>";
        }
        String html = "";
        for (SolrDocument doc : list){
            String document = "";
            //TODO: add picture for content type
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
                //TODO: exception handling
                url = null;
            }

            document += "<h2><a href=\"" + url + "\">" + fieldValue(doc, "stream_name") + "</a></h2>";
            document += "<div>";
            document += ContentTypes.getSimpleName(fieldValue(doc, "content_type")) + " | ";
            document += fieldValue(doc, "meta_creation_date")+ " | ";
            document += fieldValue(doc, "owner")+ " | ";
            document += path;
            //TODO: show multiple snippets for one document?
            String id = fieldValue(doc, "id");
            document += "</div>";
            //TODO: multiple snippets of relevant word?
            document += "<p>";
            String highlight = response.getHighlighting().get(id).get("_text_").get(0).replace("no_Spacing", "");
            document += highlight;

            document += "</p>";
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
