package com.mareike.solrsearch;

public enum ParameterType {
    QUERY("q"),
    SORT ("sort"),
    START ("start"),
    ROWS ("rows"),
    FILTERQUERY ("fq"),
    FIELDlIST ("fl"),
    CACHE ("cache"),
    BOOST("bq");

    final public String parameter;

    ParameterType(String queryString){
        parameter = queryString;
    }
}
