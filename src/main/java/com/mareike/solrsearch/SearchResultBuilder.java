package com.mareike.solrsearch;

public class SearchResultBuilder {



    public SearchResultBuilder(){

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
