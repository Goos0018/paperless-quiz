package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.Organizer;
import com.example.paperlessquiz.Team;
import com.example.paperlessquiz.round.Round;

public class Quiz {
    private String title;
    private String description;
    private String spreadsheetDocId;
    private Round[] rounds;
    private int nrOfParticipants;
    private Team[] participants;
    private Organizer Presenter;
    private Organizer Juror;
    private Organizer Corrector;
    private String status;
    private int nrOfRounds;

    public Quiz(String title, String status, int nrOfRounds) {
        this.title = title;
        this.status = status;
        this.nrOfRounds = nrOfRounds;
    }

    public String getTitle() {
        return title;
    }
}
