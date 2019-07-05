package com.mareike.solrsearch;

public enum ParameterType {
    QUERY("q"),
    SORT ("sort"),
    ROWS ("rows"),
    FILTERQUERY ("fq"),
    BOOST("bq");

    final public String parameter;

    ParameterType(String queryString){
        parameter = queryString;
    }
}
