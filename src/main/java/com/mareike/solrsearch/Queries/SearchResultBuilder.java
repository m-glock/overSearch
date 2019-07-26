package com.mareike.solrsearch.Queries;

import com.mareike.solrsearch.ContentTypes;
import com.mareike.solrsearch.Main;
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
            Main.logger.info("No documents found for the query.");
            return "<div> No documents fit your query. </div>";
        }
        String html = "";
        Main.logger.info("build HTML for list of returned documents...");
        for (SolrDocument doc : list){
            String document = "";
            String path = fieldValue(doc, "path");
            URL url;
            try {
                if(path.contains("sharepoint")){
                    url = new URL(path);
                }else{
                    File file = new File(path);
                    url = file.toURI().toURL();
                }
            }catch(MalformedURLException ex){
                Main.logger.info("Exception when building the URL to open the file.");
                url = null;
            }

            document += "<h2><a href=\"" + ((url == null) ? "" : url) + "\">" + fieldValue(doc, "stream_name") + "</a></h2>";
            document += "<div>";
            document += ContentTypes.getSimpleName(fieldValue(doc, "content_type")) + " | ";
            document += fieldValue(doc, "meta_creation_date")+ " | ";
            document += fieldValue(doc, "owner")+ " | ";
            document += path;
            String id = fieldValue(doc, "id");
            document += "</div>";
            document += "<p>";
            //TODO: more thatn one snippet?
            String highlight;
            if(response.getHighlighting().get(id).isEmpty()) {
                highlight = "No excerpt available.";
            }else{
                highlight = response.getHighlighting().get(id).get("_text_").get(0).replace("no_Spacing", "");
            }
            document += highlight;

            document += "</p>";
            html += document;
        }
        Main.logger.info("Finished building the HTML.");
        return html;
    }

    private static String fieldValue(SolrDocument doc, String fieldName){
        //returns "[true]" -> remove square brackets
        String fieldValue = (doc.getFieldValue(fieldName) == null) ? "" : doc.getFieldValue(fieldName).toString();
        return fieldValue.replaceAll("\\[", "").replaceAll("]","");
    }
}
