package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.Organizer;
import com.example.paperlessquiz.Team;
import com.example.paperlessquiz.quizgetter.QuizGetter;
import com.example.paperlessquiz.round.Round;

public class Quiz {
    private String name;
    private String description;
    private String spreadsheetDocId;
    private Round[] rounds;
    private int nrOfParticipants;
    private Team[] participants;
    private Organizer Presenter;
    private Organizer Juror;
    private Organizer Corrector;
    private boolean open;
    private int nrOfRounds;

    public Quiz() {
    }

    public Quiz(String name, String description, String spreadsheetDocId, boolean open) {
        this.name = name;
        this.description=description;
        this.spreadsheetDocId=spreadsheetDocId;
        this.open = open;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpreadsheetDocId(String spreadsheetDocId) {
        this.spreadsheetDocId = spreadsheetDocId;
    }

    public void isOpen(boolean open) {
        this.open = open;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
