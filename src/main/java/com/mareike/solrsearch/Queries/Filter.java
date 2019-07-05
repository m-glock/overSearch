package com.mareike.solrsearch.Queries;

public enum Filter{
    CREATORFILTER("filter", "owner"),
    DATEFILTER("filter", "meta_creation_date"),
    FORMATFILTER("filter", "content_type"),
    CREATORPREFERENECE("boost", "owner"),
    DATEPREFERENECE("boost", "meta_creation_date"),
    FORMATPREFERENECE("boost", "content_type"),
    SORTRELEVANCE("sort", "relevance");

    final public String type;
    final public String value;

    Filter(String parameter, String value){
        type = parameter;
        this.value = value;
    }
}