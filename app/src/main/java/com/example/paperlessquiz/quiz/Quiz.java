package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.Organizer;
import com.example.paperlessquiz.Round;
import com.example.paperlessquiz.Team;

public class Quiz
{

    private String id;
    private String title;
    private String description;
    private String spreadsheetDocId;
    private boolean isavailable;
    private int nrOfRounds;
    private Round[] rounds;
    private int nrOfParticipants;
    private Team[] participants;
    private Organizer Presenter;
    private Organizer Juror;
    private Organizer Corrector;

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
