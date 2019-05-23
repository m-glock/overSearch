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
    //private String[] values;

    ParameterType(String queryString){
        parameter = queryString;
    }

    /*public void setValues(String[] values){
        this.values = values;
    }

    public String[] getValues(){
        return values;
    }*/
}
