package com.mareike.solrsearch;

public class ContentTypes {

    public static String getSimpleName(String contentType){
        switch(contentType){
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return "Word Document";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return "Excel Sheet";
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                return "PowerPoint Presentation";
            case "application/pdf":
                return "PDF file";
            case "text/plain; charset=windows-1252":
                return "Textfile";
            default:
                return "";
        }
    }

    public static String getSolrValues(String format){
        switch(format){
            case "Word Document":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "Excel Sheet":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "PowerPoint Presentation":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "PDF":
                return "application/pdf";
            case "Textfile":
                return "text/plain; charset=windows-1252";
            default:
                return "";
        }
    }

}
