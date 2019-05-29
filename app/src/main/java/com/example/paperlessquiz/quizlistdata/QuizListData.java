package com.example.paperlessquiz.quizlistdata;

import java.io.Serializable;
import java.util.Date;

// This class stores the basic Quiz data as it can be found in the central QuizList sheet
public class QuizListData implements Serializable {
    //public static final String INTENT_EXTRA_NAME_THIS_QUIZ_BASICS = "thisQuizBasics";
    private String id;
    private String name;
    private String description;
    private String sheetDocID;
    private Date date;
    private String logoURL;

    public QuizListData() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.sheetDocID = "";
        this.logoURL = "";
    }

    public QuizListData(String id, String name, String description, String sheetDocID, String logoURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sheetDocID = sheetDocID;
        this.logoURL = logoURL;
    }

    public QuizListData(QuizListData quizListData) {
        this.id = quizListData.id;
        this.name = quizListData.name;
        this.description = quizListData.description;
        this.sheetDocID = quizListData.sheetDocID;
        this.date = quizListData.date;
        this.logoURL = quizListData.logoURL;
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

    public String getLogoURL() {
        return logoURL;
    }
}
