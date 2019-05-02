package com.example.paperlessquiz.quizgetter;

import java.io.Serializable;
import java.util.Date;

public class QuizGetter implements Serializable
{
    private String id;
    private String name;
    private String description;
    private String sheetDocID;
    private Date date;
    private boolean open;



    public QuizGetter(String id, String name, String description, String sheetDocID, boolean open) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sheetDocID = sheetDocID;
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSheetDocID() {
        return sheetDocID;
    }

    public boolean isOpen() {
        return open;
    }
}
