package com.paperlessquiz.quiz;

import java.io.Serializable;
import java.util.Date;

/*
    This class stores the basic Quiz data as it can be found in the central QuizList sheet
    Update 7/3/2020: removed QuizPDFURL + added hideTopRows
 */
public class QuizListData implements Serializable {
    private int idQuiz, hideTopRows;
    private String name;
    private String description;
    private String logoURL;

    //Constructor used by the parser
    public QuizListData(int idQuiz, int hideTopRows, String name, String description, String logoURL) {
        this.idQuiz = idQuiz;
        this.hideTopRows = hideTopRows;
        this.name = name;
        this.description = description;
        this.logoURL = logoURL;
    }

    public QuizListData() {
        //this.id = "";
        this.name = "";
        this.description = "";
        //this.sheetDocID = "";
        this.logoURL = "";
    }

    public QuizListData(String name, String description, String logoURL) {
        this.name = name;
        this.description = description;
        this.logoURL = logoURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getLogoURL() {
        return logoURL;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public int getHideTopRows() {
        return hideTopRows;
    }
}


