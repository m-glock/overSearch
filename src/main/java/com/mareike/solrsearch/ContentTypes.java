package com.mareike.solrsearch;

public class ContentTypes {

    public static String getSimpleName(String contentType){
        switch(contentType){
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return "Word";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return "Excel";
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                return "PowerPoint";
            case "application/pdf":
                return "PDF";
            case "text/plain; charset=windows-1252":
                return "Textfile";
            default:
                return "Unknown";
        }
    }

    public static String getSolrValues(String format){
        switch(format){
            case "Word":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "Excel":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "PowerPoint":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "PDF":
                return "application/pdf";
            case "Textfile":
                return "text/plain; charset=windows-1252";
            default:
                return "Unknown";
        }
    }

}
