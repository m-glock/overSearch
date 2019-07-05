package com.mareike.solrsearch.Queries;

public enum Filter{
    CREATORFILTER("filter", "owner"),
    DATEFILTER("filter", "date"),
    FORMATFILTER("filter", "format"),
    CREATORPREFERENECE("boost", "owner"),
    DATEPREFERENECE("boost", "date"),
    FORMATPREFERENECE("boost", "format"),
    SORTRELEVANCE("sort", "relevance");

    final public String type;
    final public String value;

    Filter(String parameter, String value){
        type = parameter;
        this.value = value;
    }
}