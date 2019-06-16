package com.mareike.solrsearch.Indexing;


import java.util.Date;

public class SharePointFile{

    private String name;
    private String content_Type; //TODO: Enum?
    private String _text_;
    private int id;
    private String creator;
    private String modifier; //person that modified the file - id this needed?
    private int streamSize;
    private Date dateCreated;
    private Date dateModified;
    private String teamsiteName;

    public SharePointFile(){

    }

}
