package com.mareike.solrsearch.Queries;

public enum Filter{
    CREATORFILTER("filter", "owner"),
    DATEFILTER("filter", "date"),
    FORMATFILTER("filter", "content_type"),
    CREATORPREFERENECE("boost", "owner"),
    DATEPREFERENECE("boost", "date"),
    FORMATPREFERENECE("boost", "content_type"),
    SORTRELEVANCE("sort", "relevance");

    final public String type;
    final public String value;

    Filter(String parameter, String value){
        type = parameter;
        this.value = value;
    }
}