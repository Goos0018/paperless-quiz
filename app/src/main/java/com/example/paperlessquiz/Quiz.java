package com.example.paperlessquiz;

public class Quiz
{

    private String id;
    private String title;
    private String description;
    private String spreadsheetDocId;
    private boolean isavailable;
    private int nrOfRounds;
    private Round[] rounds;

    public Quiz(String id, String title, String description, String spreadsheetDocId, boolean status, int nrOfRounds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.spreadsheetDocId = spreadsheetDocId;
        this.isavailable = status;
        this.rounds = new Round[nrOfRounds];
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSpreadsheetDocId() {
        return spreadsheetDocId;
    }

    public boolean isavailable() {
        return isavailable;
    }
}
