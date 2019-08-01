package com.paperlessquiz.quiz;

import java.io.Serializable;
import java.util.Date;

// This class stores the basic Quiz data as it can be found in the central QuizList sheet
public class QuizListData implements Serializable {
    private int idQuiz;
    private String name;
    private String description;
    private String logoURL;
    private String quizPdfURL;
    private int debugLevel;
    private String sheetDocID;

    private boolean keepLogs;
    private int nrOfRounds;
    private int appDebugLevel;

    public QuizListData(int idQuiz, String name, String description, String logoURL, String quizPdfURL, int debugLevel, String sheetDocID) {
        this.idQuiz = idQuiz;
        this.name = name;
        this.description = description;
        this.logoURL = logoURL;
        this.quizPdfURL = quizPdfURL;
        this.debugLevel = debugLevel;
        this.keepLogs = keepLogs;
        this.sheetDocID = sheetDocID;
    }

    public QuizListData() {
        //this.id = "";
        this.name = "";
        this.description = "";
        this.sheetDocID = "";
        this.logoURL = "";
    }

    public QuizListData(String name, String description, String sheetDocID, String logoURL,int debugLevel,boolean keepLogs,int appDebugLevel,String quizPdfURL) {
        //this.id = id;
        this.name = name;
        this.description = description;
        this.sheetDocID = sheetDocID;
        this.logoURL = logoURL;
        this.quizPdfURL = quizPdfURL;
        this.debugLevel=debugLevel;
        this.keepLogs=keepLogs;
        this.appDebugLevel=appDebugLevel;
    }

    /*
    public QuizListData(QuizListData quizListData) {
        this.id = quizListData.id;
        this.name = quizListData.name;
        this.description = quizListData.description;
        this.sheetDocID = quizListData.sheetDocID;
        this.date = quizListData.date;
        this.logoURL = quizListData.logoURL;
    }
    */

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

    public int getDebugLevel() {
        return debugLevel;
    }

    public boolean isKeepLogs() {
        return keepLogs;
    }

    public int getNrOfRounds() {
        return nrOfRounds;
    }

    public void setNrOfRounds(int nrOfRounds) {
        this.nrOfRounds = nrOfRounds;
    }

    public int getAppDebugLevel() {
        return appDebugLevel;
    }

    public String getQuizPdfURL() {
        return quizPdfURL;
    }

    public int getIdQuiz() {
        return idQuiz;
    }
}
