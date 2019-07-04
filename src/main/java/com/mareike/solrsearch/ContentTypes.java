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

}
